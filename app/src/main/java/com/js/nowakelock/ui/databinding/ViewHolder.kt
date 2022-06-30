package com.js.nowakelock.ui.databinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.js.nowakelock.BR
import com.js.nowakelock.ui.databinding.item.BaseItem

class ViewHolder(private var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    // 绑定
    fun bind(item: BaseItem?) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}