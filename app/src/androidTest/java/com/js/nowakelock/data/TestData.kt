package com.js.nowakelock.data

import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.WakeLock

class TestData {
    companion object {
        var appInfos =
            getInfos()

        var wakeLocks =
            getwakeLocks()

        val pN = "p1"

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

        fun getwakeLocks(): MutableList<WakeLock> {
            val tmp: MutableList<WakeLock> = mutableListOf()

            for (i in 1..10 step 1) {
                tmp.add(WakeLock(pN, "w$i", count = 1))
            }
            return tmp
        }
    }
}