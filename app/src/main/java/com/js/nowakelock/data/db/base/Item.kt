package com.js.nowakelock.data.db.base

import androidx.databinding.ObservableBoolean
import androidx.room.Ignore
import com.js.nowakelock.base.BaseItem

/**for fragment*/
open class Item : BaseItem {
    @Ignore
    open var name: String = ""

    @Ignore
    open var packageName: String = ""

    @Ignore
    open var count: Int = 0

    @Ignore
    open var blockCount: Int = 0

    @Ignore
    open var countTime: Long = 0

    @Ignore
    open var blockCountTime: Long = 0

    //for ST
    @Ignore
    open var flag: ObservableBoolean =
        ObservableBoolean().apply { this.set(true) }//kotlin boolean not work on data binding

    @Ignore
    open var allowTimeinterval: Long = 0 //no limit

    @Ignore
    override fun getID() = name

    @Ignore
    override fun getContent(): Int = count
}