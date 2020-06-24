package com.js.nowakelock.ui.databding

import androidx.recyclerview.selection.ItemKeyProvider

class StringKeyProvider(private val adapter: RecycleAdapter) :
    ItemKeyProvider<String>(SCOPE_CACHED) {
    override fun getKey(position: Int): String? =
        adapter.currentList[position].getID()

    override fun getPosition(key: String): Int =
        adapter.currentList.indexOfFirst { it.getID() == key }
}