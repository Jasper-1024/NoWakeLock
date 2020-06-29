package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.db.base.ItemInfo

@Entity(tableName = "service")
data class ServiceInfo(
    @PrimaryKey
    @ColumnInfo(name = "serviceName")
    override var name: String = "",
    @ColumnInfo(name = "service_packageName")
    override var packageName: String = "",
    @ColumnInfo(name = "service_count")
    override var count: Int = 0,
    @ColumnInfo(name = "service_blockCount")
    override var blockCount: Int = 0,
    @ColumnInfo(name = "service_countTime")
    override var countTime: Long = 0,
    @ColumnInfo(name = "service_blockCountTime")
    override var blockCountTime: Long = 0//,
) : ItemInfo()