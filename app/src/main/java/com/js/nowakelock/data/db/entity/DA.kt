package com.js.nowakelock.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class DA(
    @Embedded
    var info: Info,
    @Relation(
        parentColumn = "name_info",
        entityColumn = "name_st"
    )
    var st: St?
)
