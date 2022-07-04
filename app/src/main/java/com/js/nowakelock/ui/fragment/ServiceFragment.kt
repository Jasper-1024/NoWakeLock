package com.js.nowakelock.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.ui.fragment.fbase.FBaseFragment

class ServiceFragment : FBaseFragment() {
    override val type: Type = Type.Service
    override val layout: Int = R.layout.item_service

    //set toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(
            menu,
            setOf(
                R.id.menu_filter_user,
                R.id.menu_filter_system,
                R.id.menu_filter_all,
                R.id.menu_sort_counTime
            )
        )
        super.onCreateOptionsMenu(menu, inflater)
    }
}