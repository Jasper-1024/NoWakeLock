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
//    @PrimaryKey(autoGenerate = true)
//    var id: Int,
    @ColumnInfo(name = "wakeLock_packageName")
    var packageName: String = "",
    @PrimaryKey
    var wakeLockName: String = "",
    var flag: Boolean = true,
    @ColumnInfo(name = "wakeLock_count")
    var count: Int = 0,
    @ColumnInfo(name = "wakeLock_blockCount")
    var blockCount: Int = 0,
    @ColumnInfo(name = "wakeLock_countTime")
    var countTime: Long = 0,
    @ColumnInfo(name = "wakeLock_blockCountTime")
    var blockCountTime: Long = 0,
    //xposed used
    @Ignore
    var active: Boolean = false,
    @Ignore
    var lastApplyTime: Long = 0
) : BaseItem {
    @Ignore
    override fun getID() = wakeLockName

    @Ignore
    override fun getContent(): Int = count
}