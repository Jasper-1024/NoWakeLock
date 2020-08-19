package com.js.nowakelock.test

import androidx.annotation.LayoutRes
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.BaseItem

data class RecyclerItem(
    val item: BaseItem,
    val handler: BaseHandler,
    @LayoutRes val layoutId: Int,
    val variableId: Int
)