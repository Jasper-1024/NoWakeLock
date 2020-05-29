package com.js.nowakelock.data.db.entity

import androidx.databinding.ObservableBoolean
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.js.nowakelock.base.BaseItem
import com.js.nowakelock.data.db.base.Item

@Entity(tableName = "wakeLock")
data class WakeLock(
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
    override var blockCountTime: Long = 0,
    //for ST
    @Ignore
    override var flag: ObservableBoolean = ObservableBoolean().apply { this.set(true) },
    @Ignore
    override var allowTimeinterval: Long = 0 //no limit

) : BaseItem, Item() {
    @Ignore
    override fun getID() = name

    @Ignore
    override fun getContent(): Int = count
}
