package com.js.nowakelock.xposedhook.test

import android.content.Context
import android.net.Uri
import android.os.IBinder
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.base.WLUtil
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.xposedhook.TAG
import com.js.nowakelock.xposedhook.log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class xptest {
    companion object {

        @Volatile
        var wls = HashMap<IBinder, WakeLock>()//active wakelock

        @Volatile
        var lastAllowTiem = HashMap<String, Long>()//wakelock last allow time

        // update Setting  interval
        private var updateSetting: Long = 60000 //Save every minutes
        private var updateSettingTime: Long = 0

        //update DB interval
        private var updateDB: Long = 180000 //Save every five minutes
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

                        XpUtil2.init()

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

//                        try {
//                            log(
//                                "$TAG wakeLock: pN = $pN , tag=\"$wN\" , lock= \"${Objects.hashCode(
//                                    lock
//                                )}\"," +
//                                        "flags=0x${Integer.toHexString(flags)} , ws = \"${ws}\", " +
//                                        "uid= $uId , pid = $pid, mypid ${myPid()}"
//                            )
//                            log("$TAG wakeLock: pN = $pN ,mypid ${myPid()}")
//                        } catch (e: Exception) {
//                            log("$TAG acquireWakeLockInternal: err $e")
//                        }

                        handleWakeLockAcquire(
                            param,
                            pN,
                            wN,
                            lock,
                            uId,
                            context
                        )
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
                        handleWakeLockRelease(
                            param,
                            lock,
                            context
                        )
                    }
                })
        }

        fun handleWakeLockAcquire(
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

            // get right flag
            val flag =
                getFlag(
                    wN,
                    ArrayList<String>()
                )

            // update count / countTime
            wLup(wakeLock)

            // if block wakelock or not allowTimeinterval
            if (!flag && (SystemClock.elapsedRealtime() - wakeLock.lastAllowTime) < wakeLock.allowTimeinterval) {
                log("$TAG $pN wakeLock:$wN block")
                //block wakelock
                param.result = null
                //update blockCount / blockCountTime
                wLupBlock(
                    wakeLock
                )
            } else {
                wakeLock.lastAllowTime = SystemClock.elapsedRealtime()
            }
            wakeLock.lastApplyTime = SystemClock.elapsedRealtime()
            handleTimer(
                context
            )
        }

        fun handleWakeLockRelease(
            param: XC_MethodHook.MethodHookParam,
            lock: IBinder,
            context: Context
        ) {
            val wakeLock = wls[lock] ?: return
            //get flag
            val flag =
                getFlag(
                    wakeLock.wakeLockName,
                    ArrayList<String>()
                )

            //update Time
            wLupTime(wakeLock)
            if (!flag) {//up BlockTime
                wlupBTime(
                    wakeLock
                )
            }
            //record
            GlobalScope.launch(Dispatchers.IO) {
                record(
                    context,
                    wakeLock
                )
            }
            handleTimer(
                context
            )
            //remove index
            wls.remove(lock)
        }

        @Synchronized
        fun handleTimer(context: Context) {
            val now = SystemClock.elapsedRealtime()

            if (now - updateDBTime > updateDB) {
                GlobalScope.launch(Dispatchers.Default) {
                    try {
                        wls.keys.forEach {
                            wls[it]?.let { it1 ->
                                val flag =
                                    getFlag(
                                        it1.wakeLockName,
                                        ArrayList<String>()
                                    )
                                wLupTime(
                                    it1
                                )
                                if (!flag) {//up BlockTime
                                    wlupBTime(
                                        it1
                                    )
                                }
                                record(
                                    context,
                                    it1
                                )
                                it1.lastApplyTime = now
                            }
                        }
                    } catch (e: Exception) {
                        log("$TAG: handleTimer err: $e")
                    }
                }
                updateDBTime = now
//                log("$TAG wakeLock: update db")
            }

            if (now - updateSettingTime > updateSetting) {
                XpUtil2.reload()
                updateSettingTime = now
//                log("$TAG wakeLock: update setting")
            }
        }

        private fun wLup(wakeLock: WakeLock) {
            wakeLock.count++
            wLupTime(wakeLock)
        }

        private fun wLupBlock(wakeLock: WakeLock) {
            wakeLock.blockCount++
            wlupBTime(wakeLock)
        }

        private fun wLupTime(wakeLock: WakeLock) {
            if (wakeLock.lastApplyTime != 0.toLong()) {
                wakeLock.countTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            }
        }

        private fun wlupBTime(wakeLock: WakeLock) {
            if (wakeLock.lastApplyTime != 0.toLong()) {
                wakeLock.blockCountTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            }
        }

        private fun getFlag(wN: String, list: List<String>): Boolean =
            XpUtil2.getFlag(wN) && handleRE(
                wN,
                list
            )


        private fun handleRE(wN: String, rE: List<String>): Boolean {
            return true
        }


        fun record(context: Context, wakeLock: WakeLock) {
            val method = "saveWL"
            val url = Uri.parse("content://${XpUtil2.authority}")
            val contentResolver = context.contentResolver
            try {
                contentResolver.call(url, method, null, WLUtil.getBundle(wakeLock))
            } catch (e: Exception) {
                log("$TAG : record ${wakeLock.wakeLockName} ${wakeLock.packageName} err: $e")
            }
        }


    }

}

