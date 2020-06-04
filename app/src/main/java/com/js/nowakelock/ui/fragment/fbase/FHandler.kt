package com.js.nowakelock.ui.fragment.fbase

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.NavgraphDirections
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.clipboardCopy
import com.js.nowakelock.data.db.base.Item

class FHandler(val viewModel: FViewModel) : BaseHandler() {
    fun onClick(/*view: View,*/ item: Item) {
        viewModel.saveST(item)
    }

    fun info(view: View, item: Item) {
        val direction =
            NavgraphDirections.actionGlobalInfoFragment(item.name, item.packageName, "wakelock")
        view.findNavController().navigate(direction)
    }

    fun copy(str: String): Boolean {
        return clipboardCopy(str)
    }

    fun onTextChanged(item: Item) {
        viewModel.saveST(item)
    }
}