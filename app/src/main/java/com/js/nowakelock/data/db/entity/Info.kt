package com.js.nowakelock.data.db.entity

import androidx.room.Entity
import com.js.nowakelock.data.db.Type

@Entity(tableName = "info", primaryKeys = ["name", "type"])
data class Info(
    var name: String = "",
    var type: Type = Type.UnKnow,
    var packageName: String = "",
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0
)
