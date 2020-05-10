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
        //        private var wlModel: WLModel = mWLmodel() //wlmodel
        private val model: Model = mModel(XPM.wakelock)

//        @Volatile
//        private var wls = HashMap<IBinder, WakeLock>()//wakelock witch active

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
            val flag = flag(wN, pN, lastAllowTime[wN] ?: 0)
            // allow wakelock
            if (flag) {
                model.upCountTime(wN, pN, 0)
                lastAllowTime[wN] = SystemClock.elapsedRealtime()//update last allow time
            } else {//block wakelock
                XpUtil.log("$pN wakeLock:$wN block")
                model.upBlockCountTime(wN, pN, 0)
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
//            val flag = flag(wN, pN, lastAllowTime[wN] ?: 0)
//            if (flag) {
//                model.upCount(wN,pN)
//                lastAllowTime[wN] = SystemClock.elapsedRealtime()//update last allow time
//            } else {//block wakelock
//                log("$pN wakeLock:$wN block")
//                model.upBlockCount(wN,pN)
//                param.result = null //block wakelock
//            }
            model.handleTimer(context)
        }

        // get weather wakelock should block or not
        private fun flag(wN: String, packageName: String, aTI: Long): Boolean {
            return model.flag(wN) && model.re(wN, packageName) && model.aTi(wN, aTI)
        }
    }
}