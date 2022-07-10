package com.js.nowakelock.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class AppDA(
    @Embedded
    var info: AppInfo,
    @Relation(
        parentColumn = "packageName",
        entityColumn = "packageName"
    )
    var count: AppCount?,
    @Relation(
        parentColumn = "packageName",
        entityColumn = "packageName_st"
    )
    var st: AppSt?
)
