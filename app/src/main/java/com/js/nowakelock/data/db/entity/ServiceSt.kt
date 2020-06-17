package com.js.nowakelock.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.js.nowakelock.data.db.base.ItemSt

@Entity(tableName = "service_st")
data class ServiceSt(
    @PrimaryKey
    @ColumnInfo(name = "serviceName_st")
    override var name: String = "",
    override var flag: Boolean = true,
    override var allowTimeinterval: Long = 0,
    override var packageName: String = ""
) : ItemSt()