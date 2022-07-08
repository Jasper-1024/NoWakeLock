package com.js.nowakelock.ui.fragment.fbase

import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.ui.databinding.item.BaseItemHandle

class HandleDA(private val FBVm: FBaseViewModel) : BaseItemHandle() {
    fun saveSt(da: DA) {
        da.st?.let { FBVm.setSt(it) }
    }

    fun copy(da: DA): Boolean {
        return FBVm.copy(da.info.name)
    }
}