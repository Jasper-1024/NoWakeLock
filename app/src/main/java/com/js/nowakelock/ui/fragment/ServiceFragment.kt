package com.js.nowakelock.ui.fragment

import android.view.Menu
import android.view.MenuInflater
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.ui.fragment.fbase.FFragment
import com.js.nowakelock.ui.fragment.fbase.FViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class ServiceFragment : FFragment() {

    override val viewModel: FViewModel by inject(named("VMS")) { parametersOf(packageName) }

    override val type = "service"

    //set toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(
            menu,
            setOf(
                R.id.menu_filter_user,
                R.id.menu_filter_system,
                R.id.menu_filter_all,
                R.id.menu_filter_modified
            )
        )
        menuGone(menu, setOf(R.id.menu_sort_countime))
        super.onCreateOptionsMenu(menu, inflater)
    }
}
