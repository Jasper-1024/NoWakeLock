package com.js.nowakelock.ui.fragment.fbase

import android.view.View
import androidx.navigation.findNavController
import com.js.nowakelock.NavgraphDirections
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.ui.databinding.item.BaseItemHandle

class HandleDA(private val FBVm: FBaseViewModel) : BaseItemHandle() {
    fun saveSt(da: DA) {
        da.st?.let { FBVm.setSt(it) }
    }

    fun copy(str: String): Boolean {
        return FBVm.copy(str)
    }

    fun directToDa(view: View, da: DA) {
        val direction =
            NavgraphDirections.actionGlobalDaFragment(
                da.info.name,
                da.info.packageName,
                da.info.type.value
            )
        view.findNavController().navigate(direction)
    }
}