package com.js.nowakelock.ui.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.js.nowakelock.R

class AppFragment : Fragment() {

    private val args: AppFragmentArgs by navArgs()
    private lateinit var packageName: String

    private lateinit var adapter: AppAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        packageName = args.PackageName
        return inflater.inflate(R.layout.fragment_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = AppAdapter(this, packageName)
        viewPager = view.findViewById(R.id.app_pager)
        viewPager.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.app_tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabText(position)
        }.attach()
    }

    private fun getTabText(position: Int): String {
        return when (position) {
            0 -> getString(R.string.WakeLock)
            1 -> getString(R.string.Alarm)
            2 -> getString(R.string.Service)
            else -> ""
        }
    }

}
