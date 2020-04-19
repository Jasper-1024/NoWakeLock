package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "wakeLock_st"
//        foreignKeys = arrayOf(
//        ForeignKey(
//            entity = AppInfo::class,
//            parentColumns = arrayOf("wakeLockName"),
//            childColumns = arrayOf("wakeLockName_st"),
//            onDelete = CASCADE
//        )
//    )
)
data class WakeLock_st(
    @PrimaryKey
    @ColumnInfo(name = "wakeLockName_st")
    var wakeLockName: String = "",
    var flag: Boolean = true,
    var allowTimeinterval: Long = 216000000// 1h
)