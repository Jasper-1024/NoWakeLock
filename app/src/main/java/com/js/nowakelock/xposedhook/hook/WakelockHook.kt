package com.js.nowakelock.xposedhook.hook

import android.content.Context
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.xposedhook.XpUtil
import com.js.nowakelock.xposedhook.model.XpNSP
import com.js.nowakelock.xposedhook.model.XpRecord
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class WakelockHook {
    companion object {

        private val type = Type.Wakelock

        @Volatile
        private var wlTs = HashMap<IBinder, WLT>()//wakelock witch active

        @Volatile
        private var lastAllowTime = HashMap<String, Long>()//wakelock last allow time

        fun hookWakeLocks(lpparam: XC_LoadPackage.LoadPackageParam) {
            when (Build.VERSION.SDK_INT) {
                //Try for alarm hooks for API levels >= 31 (S or higher)
                in Build.VERSION_CODES.S..40 -> wakeLockHook31(lpparam)
                //hooks for API levels 24-30 (N ~ R)
                in Build.VERSION_CODES.N..Build.VERSION_CODES.R -> wakeLockHook24to30(lpparam)
            }
        }

        private fun wakeLockHook31(lpparam: XC_LoadPackage.LoadPackageParam) {
            //https://cs.android.com/android/platform/superproject/+/android-12.1.0_r8:frameworks/base/services/core/java/com/android/server/power/PowerManagerService.java?hl=zh-cn
            //private void acquireWakeLockInternal(IBinder lock, int displayId, int flags, String tag,
            //         String packageName, WorkSource ws, String historyTag, int uid, int pid)
            XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
                lpparam.classLoader,
                "acquireWakeLockInternal",
                IBinder::class.java,
                Int::class.javaPrimitiveType, Int::class.javaPrimitiveType,
                String::class.java, String::class.java,//wakeLockName packageName
                WorkSource::class.java,
                String::class.java, Int::class.javaPrimitiveType, Int::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {

                        val lock = param.args[0] as IBinder
                        val wN = param.args[3] as String
                        val pN = param.args[4] as String
                        val context =
                            XposedHelpers.getObjectField(param.thisObject, "mContext") as Context

                        handleWakeLockAcquire(param, pN, wN, lock, context)
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
                        handleWakeLockRelease(lock, context)
                    }
                })
        }

        private fun wakeLockHook24to30(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.power.PowerManagerService",
                lpparam.classLoader,
                "acquireWakeLockInternal",
                IBinder::class.java, Int::class.javaPrimitiveType,
                String::class.java, String::class.java,////wakeLockName packageName
                WorkSource::class.java, String::class.java,
                Int::class.javaPrimitiveType, Int::class.javaPrimitiveType,
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
                        val context =
                            XposedHelpers.getObjectField(param.thisObject, "mContext") as Context

                        handleWakeLockAcquire(param, pN, wN, lock, context)
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
                        handleWakeLockRelease(lock, context)
                    }
                })
        }

        // handle wakelock acquire
        private fun handleWakeLockAcquire(
            param: XC_MethodHook.MethodHookParam,
            pN: String, wN: String,
            lock: IBinder, context: Context
        ) {
            val now = SystemClock.elapsedRealtime() //current time

            val block = block(wN, pN, lastAllowTime[wN] ?: 0, now)

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
        private fun handleWakeLockRelease(lock: IBinder, context: Context) {
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
        private fun block(
            wN: String, packageName: String,
            lastActive: Long, now: Long
        ): Boolean {
            val xpNSP = XpNSP.getInstance()
            return xpNSP.flag(wN, packageName, type)
                    || xpNSP.aTI(now, lastActive, wN, packageName, type)
        }
    }

    data class WLT(
        val wakelockName: String,
        val packageName: String,
        var startTime: Long = 0
    )
}