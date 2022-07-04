package com.js.nowakelock.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.ui.fragment.fbase.FBaseFragment

class AlarmFragment : FBaseFragment() {
    override val type: Type = Type.Alarm
    override val layout: Int = R.layout.item_alarm

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