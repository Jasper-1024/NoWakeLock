package com.js.nowakelock.ui.databding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.BaseItem

class RecycleAdapter(private val layout: Int, private val handler: BaseHandler) :
    ListAdapter<BaseItem, ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layout, parent, false
            ), handler
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}