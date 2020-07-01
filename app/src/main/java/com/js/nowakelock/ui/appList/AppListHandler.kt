package com.js.nowakelock.ui.appList

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.base.BaseHandler
import com.js.nowakelock.data.db.entity.AppInfo

class AppListHandler(private val viewModel: AppListViewModel) : BaseHandler() {
    fun onClick(view: View, appInfo: AppInfo) {
        val direction =
            AppListFragmentDirections.actionAppListFragmentToAppFragment(
                appInfo.packageName,
                appInfo.label
            )
        view.findNavController().navigate(direction)
    }
}

