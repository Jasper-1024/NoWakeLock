package com.js.nowakelock.ui.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.js.nowakelock.ui.app.setting.AppSettingFragment
import com.js.nowakelock.ui.app.setting.AppSettingViewModel
import com.js.nowakelock.ui.wakeLock.WakeLockFragment

class AppAdapter(fragment: Fragment, val packageName: String) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = getFragment(position)
        fragment.arguments = Bundle().apply {
            putString("packageName", packageName)
        }
        return fragment
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> WakeLockFragment()
            else -> AppSettingFragment()
        }
    }
}
