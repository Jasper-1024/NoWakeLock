package com.js.nowakelock.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Service(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var serviceName: String = "",
    var flag: Boolean = true,
    var count: Int
)