package com.js.nowakelock.data.db.network

import androidx.room.Entity

@Entity(tableName = "description", primaryKeys = ["name", "language"])
data class Description(
    var name: String = "",
    var language: String = "",
    var packageName: String = "",
    var re: Boolean = false,
    var info: String = ""
)