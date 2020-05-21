package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_st")
data class Service_st(
    @PrimaryKey
    @ColumnInfo(name = "serviceName_st")
    var serviceName: String = "",
    var flag: Boolean = true,
    var allowTimeinterval: Long = 0
)