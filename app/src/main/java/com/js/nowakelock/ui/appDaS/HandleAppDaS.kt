package com.js.nowakelock.ui.appDaS

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.ui.databinding.item.BaseItemHandle

class HandleAppDaS : BaseItemHandle() {
    fun onClick(view: View, appInfo: AppInfo) {
        val direction =
            AppDaSFragmentDirections.actionAppDaFragmentToAppFragment(
                appInfo.packageName,
                appInfo.label,
                appInfo.userId
            )
        view.findNavController().navigate(direction)
    }
}