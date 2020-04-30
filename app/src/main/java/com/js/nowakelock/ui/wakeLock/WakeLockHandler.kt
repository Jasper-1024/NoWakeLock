package com.js.nowakelock.ui.wakeLock

import android.view.View
import android.widget.Switch
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.WakeLock

class WakeLockHandler(private val viewModel: WakeLockViewModel) : BaseHandler() {
    fun onClick(view: View, wakeLock: WakeLock) {
//        wakeLock.flag = wakeLock.flagproxy.get()
//        LogUtil.d("test1", "click ${wakeLock.wakeLockName}")
        viewModel.setWakeLockFlag(wakeLock)
    }

    fun onTextChanged(wakeLock: WakeLock) {
//        wakeLock.flag = wakeLock.flagproxy.get()
//        LogUtil.d("test1", "onTextChanged ${wakeLock.allowTimeinterval}")
        viewModel.setWakeLockFlag(wakeLock)
    }
}