package com.js.nowakelock.xposedhook.hook

import android.content.Context
import android.os.IBinder
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.xposedhook.XpUtil
import com.js.nowakelock.xposedhook.model.XpRecord
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class WakelockHook {
    companion object {

        @Volatile
        private var wlTs = HashMap<IBinder, WLT>()//wakelock witch active

        @Volatile
        private var lastAllowTime = HashMap<String, Long>()//wakelock last allow time

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
//                        val flags = param.args[1] as Int
                        val wN = param.args[2] as String
                        val pN = param.args[3] as String
//                        val ws = param.args[4] as WorkSource?
//                        val historyTag = param.args[5] as String
//                        val uId = param.args[6] as Int
//                        val pid = param.args[7] as Int
                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context

                        handleWakeLockAcquire(
                            param,
                            pN,
                            wN,
                            lock,
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
                            lock,
                            context
                        )
                    }
                })
        }

        // handle wakelock acquire
        private fun handleWakeLockAcquire(
            param: XC_MethodHook.MethodHookParam,
            pN: String,
            wN: String,
            lock: IBinder,
            context: Context
        ) {
            val now = SystemClock.elapsedRealtime() //current time

            val block = false

            if (block) {//block wakelock

                XpUtil.log("$pN wakeLock:$wN block")
                param.result = null

                XpRecord.upBlockCount(wN, pN, Type.Wakelock, context) //update blockCount
            } else { // allow wakelock
                lastAllowTime[wN] = now //update last allow time

                wlTs[lock] ?: WLT(wN, pN, startTime = now).let {
                    wlTs[lock] = it // add to wlT
                }
            }
        }

        //handle wakelock release
        private fun handleWakeLockRelease(
            lock: IBinder,
            context: Context
        ) {
            val now = SystemClock.elapsedRealtime() //current time
            val wlT: WLT = wlTs[lock]!!

            XpRecord.upCount(
                wlT.wakelockName, wlT.packageName, Type.Wakelock, context
            ) //update count

            XpRecord.upCountTime(
                now - wlT.startTime, wlT.wakelockName, wlT.packageName, Type.Wakelock, context
            ) //update countTime

            wlTs.remove(lock)
        }

        // get wakelock should block or not
//        private fun flag(wN: String, packageName: String, aTI: Long): Boolean {
//            return model.flag(wN) && model.re(wN, packageName) && model.aTi(wN, aTI)
//        }
    }

    data class WLT(
        val wakelockName: String,
        val packageName: String,
        var startTime: Long = 0
    )
}