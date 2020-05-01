package com.js.nowakelock.xposedhook.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.SystemClock
import com.js.nowakelock.xposedhook.log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.util.*

class AlarmHook {
    companion object {

        fun hookAlarm(lpparam: XC_LoadPackage.LoadPackageParam) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //Try for alarm hooks for API levels >= 29
                XposedHelpers.findAndHookMethod("com.android.server.AlarmManagerService",
                    lpparam.classLoader,
                    "triggerAlarmsLocked",
                    ArrayList::class.java,
                    Long::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val triggerList = param.args[0] as ArrayList<Any>
                            val nowELAPSED = param.args[1] as Long
                            val context = XposedHelpers.getObjectField(
                                param.thisObject,
                                "mContext"
                            ) as Context
//                            log("Alarm Q ${triggerList.size} $nowELAPSED")
                            hookAlarmsLocked(param, triggerList, context)
                        }
                    })
            } else {
                //Try for alarm hooks for API levels < 29 > 24.
                XposedHelpers.findAndHookMethod("com.android.server.AlarmManagerService",
                    lpparam.classLoader,
                    "triggerAlarmsLocked",
                    ArrayList::class.java,
                    Long::class.javaPrimitiveType,
                    Long::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val triggerList = param.args[0] as ArrayList<Any>
                            val nowELAPSED = param.args[1] as Long
                            val nowRTC = param.args[2] as Long
//                            log("Alarm N ${triggerList.size} $nowELAPSED $nowRTC")
                            val context = XposedHelpers.getObjectField(
                                param.thisObject,
                                "mContext"
                            ) as Context
                            hookAlarmsLocked(param, triggerList, context)
                        }
                    })
            }
        }

        private fun hookAlarmsLocked(
            param: XC_MethodHook.MethodHookParam,
            triggerList: ArrayList<Any>,
            context: Context
        ) {
            val now = SystemClock.elapsedRealtime() //real time
            for (i in 0 until triggerList.size) {
                val pi = XposedHelpers.getObjectField(triggerList[i], "operation") as PendingIntent
                val intent: Intent = getIntent(pi) ?: continue
                val alarmName: String = getName(intent) ?: continue
                val packageName: String = pi.creatorPackage ?: continue

                log("hookAlarmsLocked alarmName:$alarmName  ")
                log("hookAlarmsLocked packageName:$packageName")

            }
        }

        private fun getIntent(pi: PendingIntent): Intent? {
            try {
                return XposedHelpers.callMethod(pi, "getIntent") as Intent
            } catch (e: NoSuchMethodError) {
                try {
                    XposedHelpers.getObjectField(pi, "mTarget")?.let { mTarget ->
                        XposedHelpers.getObjectField(mTarget, "key")?.let { pIRecordKey ->
                            val requestIntent =
                                XposedHelpers.getObjectField(pIRecordKey, "requestIntent")
                            return requestIntent as Intent
                        }
                    }
                } catch (e: Exception) {
                    log("getIntent failed2 err:$e")
                }
                log("getIntent failed1 err:$e")
            }
            return null
        }

        private fun getName(intent: Intent): String? {
            //Make sure one of the tags exists.
            if (intent.action != null) {
                return intent.action
            } else if (intent.component != null) {
                return intent.component?.flattenToShortString()
            }
            return null
        }


    }
}