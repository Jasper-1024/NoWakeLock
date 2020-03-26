package com.js.nowakelock.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.repository.WakeLockRepository
import kotlinx.coroutines.launch

class WakeLockViewModel(
    private var wakeLockRepository: WakeLockRepository,
    packageName: String
) : ViewModel() {
//    init {
//        viewModelScope.launch {
//            wakeLockRepository.init()
//        }
//    }

    val TAG = "WakeLockViewModel"

    val wakelocks = wakeLockRepository.getWakeLocks(packageName)

    fun syncWakeLocks() = viewModelScope.launch {
        wakeLockRepository.insertAll()
    }
}