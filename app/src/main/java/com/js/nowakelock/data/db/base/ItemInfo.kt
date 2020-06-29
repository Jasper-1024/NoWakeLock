package com.js.nowakelock.data.db.base

import androidx.room.Ignore

/**for fragment*/
open class ItemInfo {
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
}