package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.db.base.ItemInfo

@Entity(tableName = "alarm")
data class AlarmInfo(
    @PrimaryKey
    @ColumnInfo(name = "alarmName")
    override var name: String = "",
    @ColumnInfo(name = "alarm_packageName")
    override var packageName: String = "",
    @ColumnInfo(name = "alarm_count")
    override var count: Int = 0,
    @ColumnInfo(name = "alarm_blockCount")
    override var blockCount: Int = 0,
    @ColumnInfo(name = "alarm_countTime")
    override var countTime: Long = 0,
    @ColumnInfo(name = "alarm_blockCountTime")
    override var blockCountTime: Long = 0//,
) : ItemInfo()