package com.js.nowakelock.data.db.base

import androidx.databinding.ObservableBoolean
import androidx.room.Ignore
import com.js.nowakelock.base.BaseItem

open class Item : BaseItem {
    @Ignore
    open val info: ItemInfo = ItemInfo()

    @Ignore
    open val st: ItemSt? = ItemSt()

    //kotlin boolean not work on data binding
    @Ignore
    var stFlag: ObservableBoolean = ObservableBoolean().apply { this.set(true) }

    @Ignore
    override fun getID() = info.name

    @Ignore
    override fun getContent(): Int = info.count + getBool(st?.flag)
}

fun getBool(flag: Boolean?): Int = if (flag == true) 10 else 20