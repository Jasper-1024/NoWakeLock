package com.js.nowakelock.data.db.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
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
    @ColumnInfo(name = "wakeLock_packageName")
    var packageName: String = "",
    @PrimaryKey
    var wakeLockName: String = "",
    @ColumnInfo(name = "wakeLock_count")
    var count: Int = 0,
    @ColumnInfo(name = "wakeLock_blockCount")
    var blockCount: Int = 0,
    @ColumnInfo(name = "wakeLock_countTime")
    var countTime: Long = 0,
    @ColumnInfo(name = "wakeLock_blockCountTime")
    var blockCountTime: Long = 0,
    @Ignore
    var flag: Boolean = true,//for SharedPreferences
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
}