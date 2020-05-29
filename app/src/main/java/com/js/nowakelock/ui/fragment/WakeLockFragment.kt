package com.js.nowakelock.ui.fragment

import android.app.Service
import android.view.*
import com.js.nowakelock.R
import com.js.nowakelock.base.menuGone
import com.js.nowakelock.ui.fragment.fbase.FFragment
import com.js.nowakelock.ui.fragment.fbase.FViewModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.experimental.property.inject


class WakeLockFragment : FFragment() {

    override val viewModel: FViewModel by inject(named("VMW")) { parametersOf(packageName) }

    //set toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menuGone(menu, setOf(R.id.menu_filter_user, R.id.menu_filter_system, R.id.menu_filter_all))
        super.onCreateOptionsMenu(menu, inflater)
    }
}
