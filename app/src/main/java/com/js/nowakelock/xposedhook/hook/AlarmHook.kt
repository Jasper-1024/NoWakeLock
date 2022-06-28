package com.js.nowakelock.xposedhook.hook

import android.content.Context
import android.os.Build
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.xposedhook.XpUtil
import com.js.nowakelock.xposedhook.model.IModel
import com.js.nowakelock.xposedhook.model.Model
import com.js.nowakelock.xposedhook.model.XPM
import com.js.nowakelock.xposedhook.model.XpRecord
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.util.*


class AlarmHook {
    companion object {

        fun hookAlarm(lpparam: XC_LoadPackage.LoadPackageParam) {

            when (Build.VERSION.SDK_INT) {
                //Try for alarm hooks for API levels >= 29 (Q or higher)
                in Build.VERSION_CODES.Q..40 -> alarmHook29(
                    lpparam
                )
                //Try for alarm hooks for API levels < 29 > 24.(N ~ P)
                in Build.VERSION_CODES.N..Build.VERSION_CODES.P -> alarmHook24to28(
                    lpparam
                )
            }
        }

        private fun alarmHook29(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.AlarmManagerService",
                lpparam.classLoader,
                "triggerAlarmsLocked",
                ArrayList::class.java,
                Long::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val triggerList = param.args[0] as ArrayList<*>
//                        val nowELAPSED = param.args[1] as Long
                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context
//                            log("Alarm Q ${triggerList.size} $nowELAPSED")
                        hookAlarmsLocked(
//                            param,
                            triggerList,
                            context
                        )
                    }
                })
        }

        private fun alarmHook24to28(lpparam: XC_LoadPackage.LoadPackageParam) {
            XposedHelpers.findAndHookMethod("com.android.server.AlarmManagerService",
                lpparam.classLoader,
                "triggerAlarmsLocked",
                ArrayList::class.java,
                Long::class.javaPrimitiveType,
                Long::class.javaPrimitiveType,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        val triggerList = param.args[0] as ArrayList<*>
//                        val nowELAPSED = param.args[1] as Long
//                        val nowRTC = param.args[2] as Long
//                            log("Alarm N ${triggerList.size} $nowELAPSED $nowRTC")
                        val context = XposedHelpers.getObjectField(
                            param.thisObject,
                            "mContext"
                        ) as Context
                        hookAlarmsLocked(
//                            param,
                            triggerList,
                            context
                        )
                    }
                })
        }

        // handle alarm
        private fun hookAlarmsLocked(
            triggerList: ArrayList<*>,
            context: Context
        ) {
            var alarmName: String
            var packageName: String

            for (i in 0 until triggerList.size) {

                try {
                    val tmp = triggerList[i]
                    val tmp2 = tmp.javaClass.getDeclaredField("statsTag").get(tmp) as String
                    alarmName = tmp2.replace(Regex("\\*.*\\*:"), "")
                    packageName = tmp.javaClass.getDeclaredField("packageName").get(tmp) as String
                } catch (e: Exception) {
                    XpUtil.log(" alarm: hookAlarmsLocked err:$e")
                    continue
                }

                // block or not
                val block = false

                if (block) {//block alarm

                    XpUtil.log("$packageName alarm: $alarmName block")

                    triggerList.removeAt(i)

                    XpRecord.upBlockCount(
                        alarmName, packageName, Type.Alarm, context
                    )//update blockCount

                } else {//allow alarm

                    XpRecord.upCount(
                        alarmName, packageName, Type.Alarm, context
                    )//update count
                }
            }
        }

        // get weather alarm should block or not
//        private fun block(aN: String, packageName: String): Boolean {
//            return model.flag(aN) && model.re(aN, packageName)
//        }
    }
}