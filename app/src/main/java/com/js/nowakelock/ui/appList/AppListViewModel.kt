package com.js.nowakelock.ui.appList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.AppInfoRepository
import com.js.nowakelock.data.repository.mAppInfoRepository
import kotlinx.coroutines.launch

class AppListViewModel(private var AppInfoRepository: AppInfoRepository) : ViewModel() {
    val TAG = "AppListViewModel"
    var appInfos: LiveData<List<AppInfo>> = AppInfoRepository.getAppLists()

    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            AppInfoRepository.syncAppInfos()
        }
    }

    fun syncAppInfos() = viewModelScope.launch { AppInfoRepository.syncAppInfos() }
}