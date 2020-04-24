package com.js.nowakelock.ui.wakeLock

import android.widget.Switch
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.WakeLock

class WakeLockHandler(private val viewModel: WakeLockViewModel) : BaseHandler() {
    fun onClick(view: Switch, wakeLock: WakeLock) {
        wakeLock.flag = view.isChecked
//        LogUtil.d("test1", "click ${wakeLock.wakeLockName}")
        viewModel.setWakeLockFlag(wakeLock)
    }
}