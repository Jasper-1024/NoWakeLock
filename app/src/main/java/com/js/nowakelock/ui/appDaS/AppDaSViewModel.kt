package com.js.nowakelock.ui.appDaS

import androidx.lifecycle.*
import com.js.nowakelock.base.appType
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.repository.appdas.AppDasRepo
import com.js.nowakelock.ui.base.AppType
import com.js.nowakelock.ui.base.Sort
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.Comparator

class AppDaSViewModel(private val userId: Int, private var appDasRepo: AppDasRepo) : ViewModel() {

    var appDas: LiveData<List<AppDA>> = appDasRepo.getAppDAs(userId).asLiveData()

    private val handleAppDaS = HandleAppDaS()

    // for recyclerview
    var list = MediatorLiveData<List<ItemAppDAS>>()

    init {
        viewModelScope.launch {
            sync()
        }
    }

    suspend fun sync() {
        appDasRepo.syncAppInfos(userId)
        appDasRepo.syncInfos()
    }

    fun getList(
        appDAs: List<AppDA>,
        type: AppType, query: String, sort: Sort, layout: Int
    ): List<ItemAppDAS> {
        return appDAs.appType(appType(type))
            .search(query, ::search)
            .sort(sort(sort))
            .toItemAppDAs(layout)
    }

    //return app type
    private fun appType(a: AppType): (AppDA) -> Boolean {
        return when (a) {
            AppType.User -> ::useApp
            AppType.System -> ::systemApp
            AppType.All -> ::allApp
        }
    }

    private fun useApp(appDA: AppDA) = !appDA.info.system
    private fun systemApp(appDA: AppDA) = appDA.info.system
    private fun allApp(appDA: AppDA) = true

    private fun search(appDA: AppDA) = "${appDA.info.label}${appDA.info.packageName}"

    private fun sort(sort: Sort): Comparator<AppDA> {
        return when (sort) {
            Sort.Name -> sortByName()
            Sort.Count -> sortByCount()
            Sort.CountTime -> sortByCountTime()
        }
    }

    private fun sortByName(): Comparator<AppDA> {
        return Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.info.label, s2.info.label)
        }
    }

    private fun sortByCount(): Comparator<AppDA> {
        return compareByDescending { it.count?.count ?: 0 }
    }

    private fun sortByCountTime(): Comparator<AppDA> {
        return compareByDescending { it.count?.countTime ?: 0 }
    }

    private fun List<AppDA>.toItemAppDAs(layout: Int): List<ItemAppDAS> {
        return this.map { app ->
            ItemAppDAS(app, handleAppDaS, layout)
        }
    }
}