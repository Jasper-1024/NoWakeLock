package com.js.nowakelock.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Alarm(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var alarmName: String = "",
    var flag: Boolean = true,
    var count: Int
)