package com.js.nowakelock.ui.fragment.fbase

import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.ui.databinding.item.BaseItemHandle

class HandleDA(private val FBVm: FBaseViewModel) : BaseItemHandle() {
    fun saveSt(st: St) {
        FBVm.setSt(st)
    }
}