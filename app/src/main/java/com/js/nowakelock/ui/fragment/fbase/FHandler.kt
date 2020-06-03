package com.js.nowakelock.ui.fragment.fbase

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import android.widget.Toast
import androidx.navigation.findNavController
import com.js.nowakelock.BasicApp
import com.js.nowakelock.NavgraphDirections
import com.js.nowakelock.R
import com.js.nowakelock.base.BaseHandler
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

    fun copy(item: Item): Boolean {
        val context = BasicApp.context
        val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("", item.name)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(context, context.getString(R.string.clipboard), Toast.LENGTH_LONG).show()
        return true
    }

    fun onTextChanged(item: Item) {
        viewModel.saveST(item)
    }
}