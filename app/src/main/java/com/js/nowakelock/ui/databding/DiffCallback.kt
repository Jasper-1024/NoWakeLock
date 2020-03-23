package com.js.nowakelock.ui.databding

import androidx.recyclerview.widget.DiffUtil
import com.js.nowakelock.base.BaseItem

class DiffCallback : DiffUtil.ItemCallback<BaseItem>() {
    /*是否是同一对象*/
    override fun areItemsTheSame(
        oldItem: BaseItem,
        newItem: BaseItem
    ): Boolean {
        return oldItem.getID() == newItem.getID()
    }

    /*内容是否相同*/
    override fun areContentsTheSame(
        oldItem: BaseItem,
        newItem: BaseItem
    ): Boolean {
        return oldItem.getContent() == newItem.getContent()
    }
}