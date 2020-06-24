package com.js.nowakelock.ui.databding

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class StringDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<String>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
        return when (val view = recyclerView.findChildViewUnder(event.x, event.y)) {
            null -> null
            else -> (recyclerView.getChildViewHolder(view) as RecycleAdapter.ViewHolder).getItemDetails()
        }
    }
}
