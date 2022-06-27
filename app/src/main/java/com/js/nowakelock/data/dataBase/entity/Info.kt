package com.js.nowakelock.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.dataBase.Type

@Entity(tableName = "info", primaryKeys = ["name", "type"])
data class Info(
    var name: String = "",
    var type: Type = Type.UnKnow,
    var packageName: String = "",
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0,
    var blockCountTime: Long = 0
)
