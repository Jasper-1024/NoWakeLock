package com.js.nowakelock.ui.appList

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.AppInfo

class AppListHandler : BaseHandler() {
    fun onClick(view: View, appInfo: AppInfo) {
//        LogUtil.d("test1", "click ${appInfo.appName}")
//        Toast.makeText(BasicApp.context, "click ${appInfo.appName}", Toast.LENGTH_SHORT).show()
        //send packageName to wakelockfragment and start wankelockfragment
        val direction =
            AppListFragmentDirections.actionAppListFragmentToWakeLockFragment(appInfo.packageName)
        view.findNavController().navigate(direction)
    }
}

