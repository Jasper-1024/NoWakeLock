package com.js.nowakelock.ui.appDa

import androidx.lifecycle.*
import com.js.nowakelock.base.appType
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.repository.appda.AppDaRepo
import com.js.nowakelock.ui.base.AppType
import com.js.nowakelock.ui.base.Sort
import com.js.nowakelock.ui.fragment.fbase.ItemDA
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.Comparator

class AppDaViewModel(private var appDaRepo: AppDaRepo) : ViewModel() {

    var appDas: LiveData<List<AppDA>> = appDaRepo.getAppDAs().asLiveData()

    private val handleAppDa = HandleAppDa()

    // for recyclerview
    var list = MediatorLiveData<List<ItemAppDA>>()

    init {
        viewModelScope.launch {
            sync()
        }
    }

    suspend fun sync() {
        appDaRepo.syncAppInfos()
        appDaRepo.syncInfos()
    }

    fun getList(
        appDAs: List<AppDA>,
        type: AppType, query: String, sort: Sort, layout: Int
    ): List<ItemAppDA> {
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

    private fun List<AppDA>.toItemAppDAs(layout: Int): List<ItemAppDA> {
        return this.map { app ->
            ItemAppDA(app, handleAppDa, layout)
        }
    }
}