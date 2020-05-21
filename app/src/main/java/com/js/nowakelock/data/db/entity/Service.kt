package com.js.nowakelock.data.db.entity

import androidx.databinding.ObservableBoolean
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.js.nowakelock.base.BaseItem

@Entity(tableName = "service")
data class Service(
    @PrimaryKey
    var serviceName: String = "",
    @ColumnInfo(name = "service_packageName")
    var packageName: String = "",
    @ColumnInfo(name = "service_count")
    var count: Int = 0,
    @ColumnInfo(name = "service_blockCount")
    var blockCount: Int = 0,
    @ColumnInfo(name = "service_countTime")
    var countTime: Long = 0,
    @ColumnInfo(name = "service_blockCountTime")
    var blockCountTime: Long = 0,
    //for ST
    @Ignore
    var flag: ObservableBoolean = ObservableBoolean().apply { this.set(true) },
    @Ignore
    var allowTimeinterval: Long = 0 //no limit
) : BaseItem {
    @Ignore
    override fun getID() = serviceName

    @Ignore
    override fun getContent(): Int = count
}