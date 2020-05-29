package com.js.nowakelock.data.base

import androidx.room.Ignore

open class Item_st {
    @Ignore
    open var name: String = ""

    @Ignore
    open var flag: Boolean = true

    @Ignore
    open var allowTimeinterval: Long = 0
}