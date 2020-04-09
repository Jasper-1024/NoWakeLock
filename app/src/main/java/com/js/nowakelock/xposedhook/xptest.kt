package com.js.nowakelock.xposedhook

import android.content.Context
import android.os.IBinder
import android.os.Process.myPid
import android.os.SystemClock
import android.os.WorkSource
import com.js.nowakelock.data.db.entity.WakeLock
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class xptest {
    companion object {

        var wls = HashMap<String, WakeLock>()
        var iwN = HashMap<IBinder, String>()

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
                        val pid = param.args[7] as Int

                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context

                        try {
                            log(
                                "$TAG wakeLock: pN = $pN , tag=\"$wN\" , lock= \"${Objects.hashCode(
                                    lock
                                )}\"," +
                                        "flags=0x${Integer.toHexString(flags)} , ws = \"${ws}\", " +
                                        "uid= $uId , pid = $pid, mypid ${myPid()}"
                            )
                            log("$TAG wakeLock: pN = $pN ,mypid ${myPid()}")
                        } catch (e: Exception) {
                            log("$TAG acquireWakeLockInternal: err $e")
                        }

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
                        val lock = param.args[0] as IBinder
                        handleWakeLockRelease(param, lock)
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
            val wakeLock: WakeLock = wls[wN] ?: WakeLock(pN, wN)
            wls[wN] = wakeLock
            //set index
            iwN[lock] = wN

            // get right flag
            val flag = wakeLock.flag && handleRE(wN, ArrayList<String>())

            //if not active
            if (!wakeLock.active) {
                wakeLock.active = true
            }
            // update count / countTime
            wLup(wakeLock)

            // if block wakelock
            if (!flag) {
                //block wakelock
                param.result = null
                //update blockCount / blockCountTime
                wLupBlock(wakeLock)
            }
            wakeLock.lastApplyTime = SystemClock.elapsedRealtime()
        }

        fun handleWakeLockRelease(param: XC_MethodHook.MethodHookParam, lock: IBinder) {
            val wN = iwN[lock] ?: return
            val wakeLock = wls[wN] ?: return
            //get flag ,disable active
            val flag = wakeLock.flag && handleRE(wN, ArrayList<String>())
            wakeLock.active = false
            //remove index
            iwN.remove(lock)
            //update Time
            wakeLock.countTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            if (!flag) {
                wakeLock.blockCountTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            }
        }

        private fun wLup(wakeLock: WakeLock) {
            wakeLock.count++
            if (wakeLock.lastApplyTime != 0.toLong()) {
                wakeLock.countTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            }
        }

        private fun wLupBlock(wakeLock: WakeLock) {
            wakeLock.blockCount++
            if (wakeLock.lastApplyTime != 0.toLong()) {
                wakeLock.blockCountTime += (SystemClock.elapsedRealtime() - wakeLock.lastApplyTime)
            }
        }

        private fun handleRE(wN: String, rE: List<String>): Boolean {
            return true
            TODO("handel Regular Expression")
        }
    }

}