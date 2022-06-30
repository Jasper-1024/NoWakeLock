package com.js.nowakelock.ui.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.js.nowakelock.ui.databinding.item.BaseItem

class RecycleAdapter : ListAdapter<BaseItem, ViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)//表示每个 item 都有唯一 id
    }

    // viewType 代表 layoutId ,需要覆写 getItemViewType()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    // 绑定
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // 复写 getItemViewType 返回 layoutId
    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    // very import 防止重复条目
    override fun getItemId(position: Int): Long = position.toLong()
}