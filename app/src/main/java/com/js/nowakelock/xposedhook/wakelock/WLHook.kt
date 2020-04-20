package com.js.nowakelock.xposedhook.wakelock

import android.content.Context
import android.net.Uri
import android.os.IBinder
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.base.WLUtil
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.xposedhook.TAG
import com.js.nowakelock.xposedhook.authority
import com.js.nowakelock.xposedhook.log
import com.js.nowakelock.xposedhook.test.xptest
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WLHook {
    companion object {
        private var wlModel: WLModel = mWLmodel() //wlmodel

        @Volatile
        private var wls = HashMap<IBinder, WakeLock>()//wakelock witch active

        @Volatile
        private var lastAllowTiem = HashMap<String, Long>()//wakelock last allow time

        // update Setting  interval
        private var updateSetting: Long = 60000 //Save every minutes
        private var updateSettingTime: Long = 0

        //update DB interval
        private var updateDB: Long = 180000 //Save every three minutes
        private var updateDBTime: Long = 0

        fun hookWakeLocks(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
                lpparam.classLoader,
                "acquireWakeLockInternal",
                IBinder::class.java,
                Int::class.javaPrimitiveType,
                String::class.java,//wakeLockName
                String::class.java,//packageName
                WorkSource::class.java,
                String::class.java,
                Int::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {

                        val lock = param.args[0] as IBinder
                        val flags = param.args[1] as Int
                        val wN = param.args[2] as String
                        val pN = param.args[3] as String
                        val ws = param.args[4] as WorkSource?
//                    val historyTag = param.args[5] as String
                        val uId = param.args[6] as Int
//                        val pid = param.args[7] as Int

                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context
                        handleWakeLockAcquire(param, pN, wN, lock, uId, context)
                    }
                })

            XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
                lpparam.classLoader,
                "releaseWakeLockInternal",
                IBinder::class.java,
                Int::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context
                        val lock = param.args[0] as IBinder
                        handleWakeLockRelease(param, lock, context)
                    }
                })
        }

        // handle wakelock acquire
        private fun handleWakeLockAcquire(
            param: XC_MethodHook.MethodHookParam,
            pN: String,
            wN: String,
            lock: IBinder,
            uId: Int,
            context: Context
        ) {

            // get wakelock
            val wakeLock: WakeLock = wls[lock] ?: WakeLock(wN, pN, uId)
            wls[lock] = wakeLock //add wakelock ,just in case.

            wLup(wakeLock)

            val flag = flag(wN, wlModel.getRe(pN), wlModel.getAllowTimeinterval(wN))
            // allow wakelock
            if (flag) {
                lastAllowTiem[wN] = SystemClock.elapsedRealtime()//update last allow time
            } else {//block wakelock
                log("$TAG $pN wakeLock:$wN block")
                param.result = null //block wakelock
                wLupBlock(wakeLock)
            }
            handleTimer(context)
        }

        //handle wakelock release
        private fun handleWakeLockRelease(
            param: XC_MethodHook.MethodHookParam,
            lock: IBinder,
            context: Context
        ) {

            // get wakelock
            val wakeLock: WakeLock = wls[lock] ?: return

            //record
            GlobalScope.launch(Dispatchers.IO) {
                xptest.record(context, wakeLock)
            }

            //remove index
            wls.remove(lock)

            handleTimer(context)
        }

        // get weather wakelock should block or not
        private fun flag(wN: String, list: String, aTI: Long): Boolean {
            return wlModel.getFlag(wN) && rE(wN, list) && aTI(wN, aTI)
        }

        private fun rE(wN: String, rE: String): Boolean {
            return true
        }

        private fun aTI(wN: String, aTI: Long): Boolean {
            return SystemClock.elapsedRealtime() - (lastAllowTiem[wN] ?: 0) > aTI
        }

        private fun wLup(wakeLock: WakeLock) {
            wakeLock.count++
//            wLupTime(wakeLock)
        }

        private fun wLupBlock(wakeLock: WakeLock) {
            wakeLock.blockCount++
//            wlupBTime(wakeLock)
        }

        @Synchronized
        private fun handleTimer(context: Context) {
            val now = SystemClock.elapsedRealtime()
            // update db
            if (now - updateDBTime > updateDB) {
                GlobalScope.launch(Dispatchers.Default) {
                    try {
                        wls.values.forEach {
                            record(context, it)
                        }
                    } catch (e: Exception) {
                        log("$TAG: handleTimer err: $e")
                    }
                }
                updateDBTime = now
            }
            //update setting
            if (now - updateSettingTime > updateSetting) {
                updateSettingTime = now
//                log("$TAG wakeLock: update setting")
            }
        }

        private fun record(context: Context, wakeLock: WakeLock) {
            val method = "saveWL"
            val url = Uri.parse("content://${authority}")
            val contentResolver = context.contentResolver
            try {
                contentResolver.call(url, method, null, WLUtil.getBundle(wakeLock))
            } catch (e: Exception) {
                log("$TAG : record ${wakeLock.wakeLockName} ${wakeLock.packageName} err: $e")
            }
        }

    }
}