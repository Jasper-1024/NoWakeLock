package com.js.nowakelock.ui.databding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.js.nowakelock.BR
import com.js.nowakelock.base.BaseItem

class ViewHolder(var binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BaseItem?) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}