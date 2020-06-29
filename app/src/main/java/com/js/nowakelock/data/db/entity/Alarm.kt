package com.js.nowakelock.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.js.nowakelock.data.db.base.Item

data class Alarm(
    @Embedded
    override var info: AlarmInfo,
    @Relation(
        parentColumn = "alarmName",
        entityColumn = "alarmName_st"
    )
    override var st: AlarmSt?
) : Item()