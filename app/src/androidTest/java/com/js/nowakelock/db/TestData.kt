package com.js.nowakelock.db

import com.js.nowakelock.db.entity.AppInfo
import com.js.nowakelock.db.entity.WakeLock

class TestData {
    companion object {
        var appInfos = getInfos()

        var wakeLocks = getwakeLocks()

        val packageName = "p1"

        fun getInfos(): MutableList<AppInfo> {
            val appInfos: MutableList<AppInfo> = mutableListOf()

            for (i in 1..10 step 1) {
                appInfos.add(AppInfo("p$i", i, "$i"))
            }
            return appInfos
        }

        fun getwakeLocks(): MutableList<WakeLock> {
            val tmp: MutableList<WakeLock> = mutableListOf()

            for (i in 1..10 step 1) {
                tmp.add(WakeLock("p1", "w$i"))
            }
            return tmp
        }
    }
}