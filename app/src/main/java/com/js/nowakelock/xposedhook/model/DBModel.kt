package com.js.nowakelock.xposedhook.model

import java.io.Serializable

data class DBModel(
    var dbHM: HashMap<String, DB> = HashMap()
) : Serializable

data class DB(
    var name: String,
    val packageName: String,
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0
) : Serializable