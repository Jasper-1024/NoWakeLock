package com.js.nowakelock.ui.fragment

import com.js.nowakelock.R
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.ui.fragment.fbase.FBaseFragment

class WakeLockFragment : FBaseFragment() {
    override val type: Type = Type.Wakelock
    override val layout: Int = R.layout.item_wakelock
}