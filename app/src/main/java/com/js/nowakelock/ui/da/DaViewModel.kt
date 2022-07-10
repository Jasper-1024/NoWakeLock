package com.js.nowakelock.ui.da

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.clipboardCopy
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.data.repository.da.DaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class DaViewModel(name: String, type: Type) : ViewModel(), KoinComponent {

    private val daR: DaRepo by inject(named("DaR"))

    var da = daR.getDa(name, type).asLiveData()

    fun setSt(st: St) {
        viewModelScope.launch(Dispatchers.IO) {
            daR.insertSt(st)
        }
    }

    fun copy(str: String): Boolean {
        return clipboardCopy(str)
    }
}