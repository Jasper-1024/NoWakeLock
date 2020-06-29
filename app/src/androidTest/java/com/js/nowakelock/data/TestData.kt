package com.js.nowakelock.data

import com.js.nowakelock.data.db.entity.*

class TestData {
    companion object {

        var alarms = getAlarm()

        val alarmST = AlarmSt("test", false, 10)

        var appInfos = getInfos()

        val appInfoST = AppInfoSt("test", false, 10)

        var wakeLocks = getwakeLocks()

        val pN = "p1"

        fun getAlarm(): MutableList<AlarmInfo> {
            val tmp: MutableList<AlarmInfo> = mutableListOf()

            for (i in 1..10 step 1) {
                tmp.add(AlarmInfo("a$i", pN, count = 1))
            }
            return tmp
        }

        fun getInfos(): MutableList<AppInfo> {
            val appInfos: MutableList<AppInfo> = mutableListOf()

            for (i in 1..10 step 1) {
                appInfos.add(
                    AppInfo(
                        "p$i",
                        i,
                        "$i"
                    )
                )
            }
            return appInfos
        }

        fun getwakeLocks(): MutableList<WakeLockInfo> {
            val tmp: MutableList<WakeLockInfo> = mutableListOf()

            for (i in 1..10 step 1) {
                tmp.add(WakeLockInfo(pN, "w$i", count = 1))
            }
            return tmp
        }
    }
}