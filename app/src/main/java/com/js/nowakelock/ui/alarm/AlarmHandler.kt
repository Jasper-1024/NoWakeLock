package com.js.nowakelock.ui.alarm

import android.view.View
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.Alarm

class AlarmHandler(var viewModel: AlarmViewModel) : BaseHandler() {
    fun onClick(view: View, alarm: Alarm) {
//        wakeLock.flag = wakeLock.flagproxy.get()
//        LogUtil.d("test1", "click ${wakeLock.wakeLockName}")
        viewModel.setFlag(alarm)
    }

    fun onTextChanged(alarm: Alarm) {
//        wakeLock.flag = wakeLock.flagproxy.get()
//        LogUtil.d("test1", "onTextChanged ${wakeLock.allowTimeinterval}")
        viewModel.setFlag(alarm)
    }
}