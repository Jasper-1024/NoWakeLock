package com.js.nowakelock.ui.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.js.nowakelock.ui.appDa.AppDaFragment
import com.js.nowakelock.ui.daS.AlarmFragment
import com.js.nowakelock.ui.daS.ServiceFragment
import com.js.nowakelock.ui.daS.WakeLockFragment

class AppAdapter(fragment: Fragment, val packageName: String, val userId: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = getFragment(position)
        fragment.arguments = Bundle().apply {
            putString("packageName", packageName)
            putInt("userId", userId)
        }
        return fragment
    }

    private fun getFragment(position: Int): Fragment {
        return when (position) {
            0 -> AppDaFragment()
            1 -> WakeLockFragment()
            2 -> AlarmFragment()
            3 -> ServiceFragment()
            else -> WakeLockFragment()
        }
    }
}
