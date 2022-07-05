package com.js.nowakelock.data.db.entity

import androidx.room.DatabaseView

@DatabaseView(
    "select packageName_info as packageName," +
            "sum(count) as count," +
            "sum(countTime) as countTime," +
            "sum(blockCount) as blockCount," +
            "(select (sum(countTime) / sum(count) * sum(blockCount)) from info where packageName_info = packageName_info and type_info = 'Wakelock') as blockCountTime " +
            "from info group by packageName_info"

)
data class AppCount(
    var packageName: String,
    var count: Int = 0,
    var countTime: Long = 0,
    var blockCount: Long = 0,
    var blockCountTime: Long = 0
)
