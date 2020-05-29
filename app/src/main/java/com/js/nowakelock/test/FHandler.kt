package com.js.nowakelock.test

import android.view.View
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.db.entity.WakeLock

class FHandler(val viewModel: FViewModel) : BaseHandler() {
    fun onClick(view: View, item: Item) {
        viewModel.saveST(item)
    }

    fun onTextChanged(item: Item) {
        viewModel.saveST(item)
    }
}