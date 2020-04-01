package com.js.nowakelock.ui.wakeLock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.repository.WakeLockRepository
import kotlinx.coroutines.launch

class WakeLockViewModel(
    private var WakeLockRepository: WakeLockRepository
) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            wakeLockRepository.init()
//        }
//    }

    val TAG = "WakeLockViewModel"

    fun getwakelocks(packageName: String) = WakeLockRepository.getWakeLocks(packageName)

    fun syncWakeLocks(packageName: String) = viewModelScope.launch {
//        LogUtil.d("test1",packageName)
        WakeLockRepository.syncWakelocks(packageName)
    }
}