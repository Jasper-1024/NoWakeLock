package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "appInfo_st")
data class AppInfo_st(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "packageName_st")
    var packageName: String = "",
    var flag: Boolean = true,
    var allowTimeinterval: Long = 0,
    var rE_Wakelock: Set<String>,
    var rE_Alarem: Set<String>,
    var rE_Service: Set<String>
)