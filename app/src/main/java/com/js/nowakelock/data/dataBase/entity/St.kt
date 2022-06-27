package com.js.nowakelock.data.dataBase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.dataBase.Type

@Entity(tableName = "st", primaryKeys = ["name", "type"])
data class St(
    @PrimaryKey
    var name: String = "",
    var type: Type = Type.UnKnow,
    var packageName: String = "",
    var flag: Boolean = true,
    var allowTimeInterval: Long = 0
)
