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
        return data.info.name
    }

    override fun getContent(): Int {
        val info = data.info
        val st = data.st
        return ((if (st!!.flag) 1 else 0) + st.allowTimeInterval
                + info.count * 5 + info.blockCount * 10).toInt()
    }
}