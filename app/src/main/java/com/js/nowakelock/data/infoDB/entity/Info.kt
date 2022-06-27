package com.js.nowakelock.data.infoDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "info")
data class Info(
    @PrimaryKey
    var name: String = "",
    var type: String = "",
    var packageName: String = "",
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0,
    var blockCountTime: Long = 0
)
