package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.base.Item_st

@Entity(tableName = "alarm_st")
data class Alarm_st(
    @PrimaryKey
    @ColumnInfo(name = "alarmName_st")
    override var name: String = "",
    override var flag: Boolean = true,
    override var allowTimeinterval: Long = 0
) : Item_st()