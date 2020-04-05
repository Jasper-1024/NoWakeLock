package com.js.nowakelock.ui.appList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.AppInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

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

    suspend fun userAppList(appInfos: List<AppInfo>): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos.filter { !it.system }
                .sortedWith(Comparator { s1, s2 ->
                    Collator.getInstance(Locale.getDefault()).compare(s1.appName, s2.appName)
                })
        }

    suspend fun systemAppList(appInfos: List<AppInfo>): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos.filter { it.system }
                .sortedWith(Comparator { s1, s2 ->
                    Collator.getInstance(Locale.getDefault()).compare(s1.appName, s2.appName)
                })
        }

    suspend fun countAppList(appInfos: List<AppInfo>): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos.sortedWith(Comparator { s1, s2 ->
                Collator.getInstance(
                    Locale.getDefault()
                ).compare(s1.appName, s2.appName)
            }).sortedBy { it.count }
        }

    suspend fun appList(appInfos: List<AppInfo>): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos.sortedWith(Comparator { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.appName, s2.appName)
            })
        }
}