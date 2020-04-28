package com.js.nowakelock.data.db.entity

import androidx.databinding.ObservableBoolean
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.js.nowakelock.base.BaseItem

@Entity(
    tableName = "wakeLock"
//    foreignKeys = arrayOf(
//        ForeignKey(
//            entity = AppInfo::class,
//            parentColumns = arrayOf("packageName"),
//            childColumns = arrayOf("wakeLock_packageName"),
//            onDelete = CASCADE
//        )
//    )
)
data class WakeLock(
    @PrimaryKey
    var wakeLockName: String = "",
    @ColumnInfo(name = "wakeLock_packageName")
    var packageName: String = "",
    @ColumnInfo(name = "wakeLock_uid")
    var uid: Int = 0,
    @ColumnInfo(name = "wakeLock_count")
    var count: Int = 0,
    @ColumnInfo(name = "wakeLock_blockCount")
    var blockCount: Int = 0,
    @ColumnInfo(name = "wakeLock_countTime")
    var countTime: Long = 0,
    @ColumnInfo(name = "wakeLock_blockCountTime")
    var blockCountTime: Long = 0,
    //for ST
    @Ignore
    var flag: ObservableBoolean = ObservableBoolean().apply { this.set(true) },
    @Ignore
    var allowTimeinterval: Long = 0,//no limit
    //for xposed
    @Ignore
    var active: Boolean = false,
    @Ignore
    var lastApplyTime: Long = 0,
    @Ignore
    var lastAllowTime: Long = 0
) : BaseItem {
    @Ignore
    override fun getID() = wakeLockName

    @Ignore
    override fun getContent(): Int = count

//    @Ignore
//    var flagproxy = ObservableBoolean().apply { this.set(flag) }
}
