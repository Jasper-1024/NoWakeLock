package com.js.nowakelock.ui.wakeLock

import android.view.View
import android.widget.Switch
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.WakeLock

class WakeLockHandler(private val viewModel: WakeLockViewModel) : BaseHandler() {
    fun onClick(view: View, wakeLock: WakeLock) {
        viewModel.setWakeLockFlag(wakeLock)
    }

    fun onTextChanged(wakeLock: WakeLock) {
        viewModel.setWakeLockFlag(wakeLock)
    }
}