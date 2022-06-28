package com.js.nowakelock.data.db.entity

import androidx.room.Entity
import com.js.nowakelock.data.db.Type

@Entity(tableName = "st", primaryKeys = ["name", "type"])
data class St(
    var name: String = "",
    var type: Type = Type.UnKnow,
    var packageName: String = "",
    var flag: Boolean = true,
    var allowTimeInterval: Long = 0
)
