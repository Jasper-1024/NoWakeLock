package com.js.nowakelock.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.js.nowakelock.data.db.base.Item

data class Service(
    @Embedded
    override var info: ServiceInfo,
    @Relation(
        parentColumn = "serviceName",
        entityColumn = "serviceName_st"
    )
    override var st: ServiceSt?
) : Item()