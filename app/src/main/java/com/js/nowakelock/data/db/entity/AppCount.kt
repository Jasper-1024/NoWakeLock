package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.DatabaseView

@DatabaseView(
    "select packageName_info as packageName_count," +
            "userid_info as userId_count," +
            "sum(count) as count," +
            "sum(countTime) as countTime," +
            "sum(blockCount) as blockCount," +
            "(select (sum(countTime / count * blockCount)) from info where packageName_info = packageName_info and userid_info = userid_info and type_info = 'Wakelock') as blockCountTime " +
            "from info group by packageName_info,userid_info"

)
data class AppCount(
    @ColumnInfo(name = "packageName_count")
    var packageName: String,
    @ColumnInfo(name = "userId_count")
    var userId: Int,
    var count: Int = 0,
    var countTime: Long = 0,
    var blockCount: Int = 0,
    var blockCountTime: Long = 0
)
