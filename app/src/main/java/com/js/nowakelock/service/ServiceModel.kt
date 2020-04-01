package com.js.nowakelock.service

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.repository.WakeLockRepository
import kotlinx.coroutines.launch

class ServiceModel(
    private var wakeLockRepository: WakeLockRepository
) : ViewModel() {
    val TAG = "ServiceModel"

    fun upCount(packageName: String?, wakelockName: String?) {
        viewModelScope.launch {
            if (packageName != null && wakelockName != null) {
                wakeLockRepository.upCount(packageName, wakelockName)
            }
        }
    }

    fun upBlockCount(packageName: String?, wakelockName: String?) {
        viewModelScope.launch {
            if (packageName != null && wakelockName != null) {
                wakeLockRepository.upBlockCount(packageName, wakelockName)
            }
        }
    }

    fun getFlag(packageName: String?, wakelockName: String?): Boolean {
        var tmp = true
        viewModelScope.launch {
            if (packageName != null && wakelockName != null) {
                tmp = wakeLockRepository.getFlag(packageName, wakelockName)
            }
        }
        return tmp
    }

    @VisibleForTesting
    fun test(packageName: String?, wakelockName: String?): Boolean {
        return packageName != null && wakelockName != null
    }
}
