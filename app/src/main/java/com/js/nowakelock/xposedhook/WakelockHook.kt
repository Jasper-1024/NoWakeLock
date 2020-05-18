package com.js.nowakelock.xposedhook

import android.content.Context
import android.os.IBinder
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.xposedhook.model.Model
import com.js.nowakelock.xposedhook.model.XPM
import com.js.nowakelock.xposedhook.model.mModel
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class WakelockHook {
    companion object {

        private val model: Model = mModel(XPM.wakelock)

        @Volatile
        private var wlT = HashMap<IBinder, WLT>()//wakelock witch active

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
                        val ws = param.args[4] as WorkSource?
//                    val historyTag = param.args[5] as String
                        val uId = param.args[6] as Int
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

        // handle wakelock acquire
        private fun handleWakeLockAcquire(
            param: XC_MethodHook.MethodHookParam,
            pN: String,
            wN: String,
            lock: IBinder,
            uId: Int,
            context: Context
        ) {
            val now = SystemClock.elapsedRealtime()

            val wlt: WLT = wlT[lock] ?: WLT(wN, pN, true, now)
            wlT[lock] = wlt

            time(lock, now)//record

            wlt.flag = flag(wN, pN, lastAllowTime[wN] ?: 0)
            wlt.lastTime = now

            // allow wakelock
            if (wlt.flag) {
                lastAllowTime[wN] = now//update last allow time
            } else {//block wakelock
                XpUtil.log("$pN wakeLock:$wN block")
                param.result = null //block wakelock
            }
            model.handleTimer(context)
        }

        //handle wakelock release
        private fun handleWakeLockRelease(
            param: XC_MethodHook.MethodHookParam,
            lock: IBinder,
            context: Context
        ) {
            val now = SystemClock.elapsedRealtime()
            time(lock, now)
            wlT.remove(lock)

            model.handleTimer(context)//handler timer
        }

        // get weather wakelock should block or not
        private fun flag(wN: String, packageName: String, aTI: Long): Boolean {
            return model.flag(wN) && model.re(wN, packageName) && model.aTi(wN, aTI)
        }

        private fun time(lock: IBinder, now: Long) {
            val wlt = wlT[lock]
            if (wlt != null && wlt.lastTime != 0.toLong()) {
                if (wlt.flag) {
                    model.upCount(wlt.wakelockName, wlt.packageName)
                    model.upCountTime(wlt.wakelockName, wlt.packageName, now - wlt.lastTime)
                } else {
                    model.upBlockCount(wlt.wakelockName, wlt.packageName)
                    model.upBlockCountTime(wlt.wakelockName, wlt.packageName, now - wlt.lastTime)
                }
            }
        }
    }

    data class WLT(
        val wakelockName: String,
        val packageName: String,
        var flag: Boolean = true,
        var lastTime: Long = 0
//        val startTime: Long = 0
//        val stopTime: Long = 0
//        val lastAAT: Long = 0,//lastActiveAllowTime
//        val lastABT: Long = 0 //lastActiveBlockTime
    )
}