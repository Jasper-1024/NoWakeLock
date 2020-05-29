package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.db.base.ItemSt

@Entity(tableName = "alarm_st")
data class AlarmSt(
    @PrimaryKey
    @ColumnInfo(name = "alarmName_st")
    override var name: String = "",
    override var flag: Boolean = true,
    override var allowTimeinterval: Long = 0
) : ItemSt()