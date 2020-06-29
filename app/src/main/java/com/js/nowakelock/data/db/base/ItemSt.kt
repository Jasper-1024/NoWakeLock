package com.js.nowakelock.data.db.base

import androidx.databinding.ObservableBoolean
import androidx.room.Ignore

/**for fragment*/
open class ItemSt(
    @Ignore
    open var name: String = "",

    @Ignore
    open var packageName: String = "",

    @Ignore
    open var flag: Boolean = true,

    @Ignore
    open var allowTimeinterval: Long = 0,

    //kotlin boolean not work on data binding
    @Ignore
    var stFlag: ObservableBoolean = ObservableBoolean().apply { this.set(true) }
)
