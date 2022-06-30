package com.js.nowakelock.ui.databinding.item

import androidx.annotation.LayoutRes

open class BaseItem(
    open val data: Any,
    open val handle: BaseItemHandle,
    @LayoutRes open val layoutId: Int
) {
    open fun getID(): String = ""//两个item 是不是一个 item
    open fun getContent(): Int = 0 //同一个 item 内容是否有更新
}
