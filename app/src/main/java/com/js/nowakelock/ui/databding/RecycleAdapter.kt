package com.js.nowakelock.ui.databding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.js.nowakelock.BR
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.BaseItem

class RecycleAdapter(private val layout: Int, private val handler: BaseHandler) :
    ListAdapter<BaseItem, RecycleAdapter.ViewHolder>(DiffCallback()) {

    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<String>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layout, parent, false
            ), handler
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (tracker != null) {
            val item = getItem(position)
            holder.bind(item, tracker!!.isSelected(item.getID()))
        } else {
            holder.bind(getItem(position))
        }
    }

    // very import
    override fun getItemId(position: Int): Long = position.toLong()


    inner class ViewHolder(var binding: ViewDataBinding, private val handler: BaseHandler) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BaseItem?) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
        }

        fun bind(item: BaseItem?, isActivated: Boolean) {
            binding.setVariable(BR.item, item)
            binding.setVariable(BR.handler, handler)
            binding.executePendingBindings()
            binding.root.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): String? = getItem(adapterPosition).getID()
//                override fun inSelectionHotspot(e: MotionEvent): Boolean {
//                    return true
//                }
            }
    }
}