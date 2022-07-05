package com.js.nowakelock.ui.appDa

import androidx.annotation.LayoutRes
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.ui.databinding.item.BaseItem

class ItemAppDA(
    override var data: AppDA,
    override val handle: HandleAppDa,
    @LayoutRes override val layoutId: Int
) : BaseItem(data, handle, layoutId) {
    override fun getID(): String {
        return data.info.packageName + data.info.label
    }

    override fun getContent(): Int {
        val appCount = data.count

        return appCount?.let {
            (it.count + it.blockCount + it.countTime).toInt()
        } ?: 0
    }
}