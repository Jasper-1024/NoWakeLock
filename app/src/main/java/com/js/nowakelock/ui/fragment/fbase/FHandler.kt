package com.js.nowakelock.ui.fragment.fbase

import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.base.Item

class FHandler(val viewModel: FViewModel) : BaseHandler() {
    fun onClick(/*view: View,*/ item: Item) {
        viewModel.saveST(item)
    }

    fun onTextChanged(item: Item) {
        viewModel.saveST(item)
    }
}