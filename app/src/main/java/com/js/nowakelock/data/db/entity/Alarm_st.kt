package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm_st")
data class Alarm_st(
    @PrimaryKey
    @ColumnInfo(name = "alarmName_st")
    var alarmName: String = "",
    var flag: Boolean = true,
    var allowTimeinterval: Long = 0
)