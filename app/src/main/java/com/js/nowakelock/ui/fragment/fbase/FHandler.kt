package com.js.nowakelock.ui.fragment.fbase

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.NavgraphDirections
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt

class FHandler(val viewModel: FViewModel, val type: String) : BaseHandler() {

    fun onClick(/*view: View,*/ itemSt: ItemSt) {
        viewModel.saveST(itemSt)
    }

    fun info(view: View, item: Item) {
        val direction =
            NavgraphDirections.actionGlobalInfoFragment(item.info.name, item.info.packageName, type)
        view.findNavController().navigate(direction)
    }

//    fun copy(str: String): Boolean {
//        return clipboardCopy(str)
//    }

    fun onTextChanged(itemSt: ItemSt) {
        viewModel.saveST(itemSt)
    }
}