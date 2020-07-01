package com.js.nowakelock.ui.appList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.*
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.appinforepository.AppInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

class AppListViewModel(private var AppInfoRepository: AppInfoRepository) : ViewModel() {
    var appInfos: LiveData<List<AppInfo>> = AppInfoRepository.getAppLists()

    init {
        viewModelScope.launch {
            AppInfoRepository.syncAppInfos()
        }
    }

    fun syncAppInfos() = viewModelScope.launch { AppInfoRepository.syncAppInfos() }

    suspend fun list(appInfos: List<AppInfo>, cache: cache): List<AppInfo> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos.app(app(cache.app))
                .search(cache.query, ::search)
                .sort(sort(cache.sort))
        }

    //return app method
    private fun app(a: Int): (AppInfo) -> Boolean {
        return when (a) {
            Status.userApp -> ::useApp
            Status.systemApp -> ::systemApp
            Status.allApp -> ::allApp
            else -> ::allApp
        }
    }

    private fun useApp(appInfo: AppInfo) = !appInfo.system
    private fun systemApp(appInfo: AppInfo) = appInfo.system
    private fun allApp(appInfo: AppInfo) = true

    private fun search(appInfo: AppInfo) = "${appInfo.label}${appInfo.packageName}"

    private fun sort(sort: Int): Comparator<AppInfo> {
        return when (sort) {
            Status.sortByName -> sortByName()
            Status.sortByCount -> sortByCount()
            Status.sortByCountTime -> sortByCountTime()
            else -> sortByName()
        }
    }

    private fun sortByName(): Comparator<AppInfo> {
        return Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.label, s2.label)
        }
    }

    private fun sortByCount(): Comparator<AppInfo> {
        return compareByDescending { it.count }
    }

    private fun sortByCountTime(): Comparator<AppInfo> {
        return compareByDescending { it.countTime }
    }

//    suspend fun AppList(appInfos: List<AppInfo>, status: Int, query: String): List<AppInfo> =
//        withContext(Dispatchers.Default) {
//            return@withContext appInfos
//                .status(status)
//                .search(query)
//                .sortByName(status)
//        }

//    fun List<AppInfo>.status(status: Int): List<AppInfo> {
//        return when (status) {
//            1 -> this.filter { !it.system }
//            2 -> this.filter { it.system }
//            3 -> this.filter { true }
//            4 -> this.sortedByDescending { it.count }
//            6 -> this.sortedByDescending { it.countTime }
//            else -> this
//        }
//    }

//    fun List<AppInfo>.search(query: String): List<AppInfo> {
//        /*lowerCase and no " " */
//        val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
//        if (q == "") {
//            return this
//        }
//        return this.filter {
//            it.label.toLowerCase(Locale.ROOT).contains(q)
//                    || it.packageName.toLowerCase(Locale.ROOT).contains(q)
//        }
//    }

//    fun List<AppInfo>.sortByName(status: Int): List<AppInfo> {
//        return if (status != 4) {
//            this.sortedWith(Comparator { s1, s2 ->
//                Collator.getInstance(Locale.getDefault()).compare(s1.label, s2.label)
//            })
//        } else {
//            this
//        }
//    }

}