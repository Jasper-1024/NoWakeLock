package com.js.nowakelock.xposedhook.alarm

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import com.js.nowakelock.xposedhook.authority
import com.js.nowakelock.xposedhook.log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class AlarmHook {
    companion object {

        // alarm model
        private val alModel: AlarmModel = mAlarmModel()

        // update Setting interval
        private var updateSetting: Long = 60000 //Save every minutes
        private var updateSettingTime: Long = 0

        fun hookAlarm(lpparam: XC_LoadPackage.LoadPackageParam) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                //Try for alarm hooks for API levels >= 29 (Q or higher)
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
                //Try for alarm hooks for API levels < 29 > 24.(N ~ P)
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

        // handle alarm
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

//                log("hookAlarmsLocked alarmName:$alarmName  ")
//                log("hookAlarmsLocked packageName:$packageName")

                // block or not
                val flag = flag(alarmName, alModel.getRe(packageName))

                if (flag) {
                    //allow alarm
                    recordUp(alarmName, packageName, context)
                } else {
                    //block alarm
                    log("$$packageName alarm:$alarmName block")
                    triggerList.remove(i)
                    recordUpBlock(alarmName, packageName, context)
                }
                handleTimer(context)
            }
        }

        @Synchronized
        private fun handleTimer(context: Context) {
            val now = SystemClock.elapsedRealtime()
            //update setting
            if (now - updateSettingTime > updateSetting) {
                alModel.reloadst(context)
                updateSettingTime = now
                log("alarm: update setting")
            }
        }

        // get weather alarm should block or not
        private fun flag(name: String, list: Set<String>): Boolean {
            return alModel.getFlag(name) && rE(name, list)
        }

        // match regular expression
        private fun rE(name: String, rE: Set<String>): Boolean {
            if (rE.isEmpty()) {
                return true
            } else {
                rE.forEach {
                    if (name.matches(Regex(it))) {
                        return false
                    }
                }
                return true
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

        //alarm name
        private fun getName(intent: Intent): String? {
            if (intent.action != null) {
                return intent.action
            } else if (intent.component != null) {
                return intent.component?.flattenToShortString()
            }
            return null
        }

        //record allow alarm
        private fun recordUp(alarmName: String, packageName: String, context: Context) {
            GlobalScope.launch {
                try {
                    record(context, upCount(alarmName, packageName))
                } catch (e: Exception) {
                    log("recordUp err:$e")
                }
            }
        }

        //record block alarm
        private fun recordUpBlock(alarmName: String, packageName: String, context: Context) {
            GlobalScope.launch {
                try {
                    record(context, upBlockCount(alarmName, packageName))
                } catch (e: Exception) {
                    log("recordUp err:$e")
                }
            }
        }

        private fun record(context: Context, bundle: Bundle) {
            val method = "saveAL"
            val url = Uri.parse("content://${authority}")
            val contentResolver = context.contentResolver
            try {
                contentResolver.call(url, method, null, bundle)
            } catch (e: Exception) {
                log("record err: $e")
            }
        }

        private fun upCount(alarmName: String, packageName: String): Bundle {
            return Bundle().apply {
                this.putString("AlarmName", alarmName)
                this.putString("PackageName", packageName)
                this.putInt("Count", 1)
                this.putInt("BlockCount", 0)
            }
        }

        private fun upBlockCount(alarmName: String, packageName: String): Bundle {
            return Bundle().apply {
                this.putString("AlarmName", alarmName)
                this.putString("PackageName", packageName)
                this.putInt("Count", 1)
                this.putInt("BlockCount", 1)
            }
        }
    }
}