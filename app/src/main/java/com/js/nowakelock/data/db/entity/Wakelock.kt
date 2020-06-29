package com.js.nowakelock.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.js.nowakelock.data.db.base.Item

data class Wakelock(
    @Embedded
    override var info: WakeLockInfo,
    @Relation(
        parentColumn = "wakeLockName",
        entityColumn = "wakeLockName_st"
    )
    override var st: WakeLockSt?
) : Item()