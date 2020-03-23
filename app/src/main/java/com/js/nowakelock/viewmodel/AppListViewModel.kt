package com.js.nowakelock.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.AppInfoRepository
import kotlinx.coroutines.launch

class AppListViewModel(private var appInfoRepository: AppInfoRepository) : ViewModel() {
    val TAG = "AppListViewModel"
    var appInfos: LiveData<List<AppInfo>> = appInfoRepository.getAppLists()

    init {
        viewModelScope.launch {
            // Coroutine that will be canceled when the ViewModel is cleared.
            appInfoRepository.syncAppInfos()
        }
    }
}