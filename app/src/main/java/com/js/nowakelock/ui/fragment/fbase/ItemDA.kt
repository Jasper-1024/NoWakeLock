package com.js.nowakelock.ui.fragment.fbase

import androidx.annotation.LayoutRes
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.ui.databinding.item.BaseItem

class ItemDA(
    override var data: DA,
    override val handle: HandleDA,
    @LayoutRes override val layoutId: Int
) : BaseItem(data, handle, layoutId) {

    override fun getID(): String {
        return data.info.name + data.info.packageName + data.info.type.value
    }

    override fun getContent(): Int {
        val info = data.info
        return info.count * 5 + info.blockCount * 10
    }
}