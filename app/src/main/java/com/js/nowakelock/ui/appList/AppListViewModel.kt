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

    suspend fun AppList(appInfos: List<AppInfo>, status: Int, query: String): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos
                .status(status)
                .search(query)
                .sortByName(status)
        }

    fun List<AppInfo>.status(status: Int): List<AppInfo> {
        return when (status) {
            1 -> this.filter { !it.system }
            2 -> this.filter { it.system }
            3 -> this.filter { true }
            4 -> this.sortedByDescending { it.count }
            else -> this
        }
    }

    fun List<AppInfo>.search(query: String): List<AppInfo> {
        /*lowerCase and no " " */
        val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
        if (q == "") {
            return this
        }
        return this.filter {
            it.label.toLowerCase(Locale.ROOT).contains(q)
                    || it.packageName.toLowerCase(Locale.ROOT).contains(q)
        }
    }

    fun List<AppInfo>.sortByName(status: Int): List<AppInfo> {
        return if (status != 4) {
            this.sortedWith(Comparator { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.label, s2.label)
            })
        } else {
            this
        }
    }


}