package com.js.nowakelock.ui.fragment

import com.js.nowakelock.R
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.ui.fragment.fbase.FBaseFragment

class ServiceFragment : FBaseFragment() {
    override val type: Type = Type.Service
    override val layout: Int = R.layout.item_service
}