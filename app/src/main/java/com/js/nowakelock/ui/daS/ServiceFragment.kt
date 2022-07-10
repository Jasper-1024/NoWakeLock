package com.js.nowakelock.ui.daS

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.ui.daS.fbase.FBaseFragment

class ServiceFragment : FBaseFragment() {
    override val type: Type = Type.Service
    override val layout: Int = R.layout.item_service

    override fun setMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuGone(
                    menu,
                    setOf(
                        R.id.menu_filter_user,
                        R.id.menu_filter_system,
                        R.id.menu_filter_all,
                        R.id.menu_sort_counTime
                    )
                )
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = true
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}