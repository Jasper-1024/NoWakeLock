package com.js.nowakelock.ui.da

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.clipboardCopy
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.data.repository.da.DaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class DaViewModel(name: String, type: Type, userId: Int) : ViewModel(), KoinComponent {

    private val daR: DaRepo by inject(named("DaR"))

    var da = daR.getDa(name, type, userId).asLiveData()

    private val stSave = MutableStateFlow(St())

    @OptIn(FlowPreview::class)
    fun saveSt() {
        viewModelScope.launch(Dispatchers.IO) {
            stSave
                .filter { it.isNotEmpty() }// 过滤空内容，避免无效网络请求
                .debounce(1000) // 300ms防抖
                .collect {
                    daR.insertSt(it)
//                    LogUtil.d("flow2", it.toString())
                }
        }
    }

    fun setSt(st: St) {
        viewModelScope.launch(Dispatchers.IO) {
            stSave.update {
                st.copy()
            }
        }
    }

    fun copy(str: String): Boolean {
        return clipboardCopy(str)
    }
}