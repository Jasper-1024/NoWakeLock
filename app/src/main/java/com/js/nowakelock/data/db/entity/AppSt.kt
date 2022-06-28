package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appSt")
data class AppSt(
    @PrimaryKey
    @ColumnInfo(name = "packageName_st")
    var packageName: String = "",
    var wakelock: Boolean = false,
    var alarm: Boolean = false,
    var service: Boolean = false,
    var rE_Wakelock: Set<String> = mutableSetOf(),
    var rE_Alarm: Set<String> = mutableSetOf(),
    var rE_Service: Set<String> = mutableSetOf()
)
