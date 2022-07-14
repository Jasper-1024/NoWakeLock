package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.js.nowakelock.data.db.Type

@Entity(tableName = "st", primaryKeys = ["name_st", "type_st", "userId"])
data class St(
    @ColumnInfo(name = "name_st")
    var name: String = "",
    @ColumnInfo(name = "type_st")
    var type: Type = Type.UnKnow,
    @ColumnInfo(name = "packageName_st")
    var packageName: String = "",
    var flag: Boolean = false,
    var allowTimeInterval: Long = 0,
    @ColumnInfo(defaultValue = "0")
    var userId: Int = 0,
)
