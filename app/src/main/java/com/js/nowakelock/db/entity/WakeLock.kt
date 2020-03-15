package com.js.nowakelock.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "wakeLock",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = AppInfo::class,
            parentColumns = arrayOf("packageName"),
            childColumns = arrayOf("WakeLock_packageName"),
            onDelete = CASCADE
        )
    )
)
data class WakeLock(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int,
    @ColumnInfo(name = "WakeLock_packageName")
    var packageName: String = "",
    @PrimaryKey
    var wakeLockName: String = "",
    var flag: Boolean = true,
    var count: Int = 0,
    var blockCount: Int = 0
)