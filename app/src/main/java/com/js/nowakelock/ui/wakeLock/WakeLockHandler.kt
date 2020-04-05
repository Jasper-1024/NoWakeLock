package com.js.nowakelock.ui.wakeLock

import android.view.View
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.WakeLock

class WakeLockHandler(private val viewModel: WakeLockViewModel) : BaseHandler() {
    fun onClick(view: View, wakeLock: WakeLock) {
//        LogUtil.d("test1", "click ${wakeLock.wakeLockName}")
        viewModel.setWakeLockFlag(wakeLock)
//        Toast.makeText(BasicApp.context, "click ${appInfo.appName}", Toast.LENGTH_SHORT).show()
//        val direction =
//            AppListFragmentDirections.actionAppListFragmentToWakeLockFragment(appInfo.packageName)
//        view.findNavController().navigate(direction)
    }
}