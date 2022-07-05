package com.js.nowakelock.ui.appDa

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.ui.databinding.item.BaseItemHandle

class HandleAppDa : BaseItemHandle() {
    fun onClick(view: View, appInfo: AppInfo) {
        val direction =
            AppDaFragmentDirections.actionAppDaFragmentToAppFragment(
                appInfo.packageName,
                appInfo.label
            )
        view.findNavController().navigate(direction)
    }
}