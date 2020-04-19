package com.js.nowakelock.ui.appList.list

import android.view.View
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.WakeLock

class ALWakeLockHandler(private val viewModelAL: ALWakeLockViewModel) : BaseHandler() {
    fun onClick(view: View, wakeLock: WakeLock) {
        viewModelAL.setWakeLock_st(wakeLock)
//        viewModelAL.setWakeLockFlag(wakeLock)
//        Toast.makeText(BasicApp.context, "click ${appInfo.appName}", Toast.LENGTH_SHORT).show()
//        val direction =
//            AppListFragmentDirections.actionAppListFragmentToWakeLockFragment(appInfo.packageName)
//        view.findNavController().navigate(direction)
    }
}