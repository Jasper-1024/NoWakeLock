package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.db.base.ItemInfo

@Entity(tableName = "wakeLock")
data class WakeLockInfo(
    @PrimaryKey
    @ColumnInfo(name = "wakeLockName")
    override var name: String = "",
    @ColumnInfo(name = "wakeLock_packageName")
    override var packageName: String = "",
    @ColumnInfo(name = "wakeLock_uid")
    var uid: Int = 0,
    @ColumnInfo(name = "wakeLock_count")
    override var count: Int = 0,
    @ColumnInfo(name = "wakeLock_blockCount")
    override var blockCount: Int = 0,
    @ColumnInfo(name = "wakeLock_countTime")
    override var countTime: Long = 0,
    @ColumnInfo(name = "wakeLock_blockCountTime")
    override var blockCountTime: Long = 0//,
) : ItemInfo()
