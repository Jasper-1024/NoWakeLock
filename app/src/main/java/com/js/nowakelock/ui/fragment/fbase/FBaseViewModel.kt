package com.js.nowakelock.ui.fragment.fbase

import androidx.lifecycle.*
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.data.repository.FR
import com.js.nowakelock.ui.base.Sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.Comparator

class FBaseViewModel(private var packageName: String = "", private var fR: FR) : ViewModel() {
    var das: LiveData<List<DA>> =
        if (packageName == "") fR.getDAs().asLiveData()
        else fR.getDAs(packageName).asLiveData()

    private val handleDA = HandleDA(this)

    // for recyclerview
    var list = MediatorLiveData<List<ItemDA>>()

    init {
        syncInfos()
    }


    fun syncInfos() {
        viewModelScope.launch(Dispatchers.IO) {
            val infos = fR.getCPInfos(packageName)
            fR.insertInfos(infos)
        }
    }

    fun setSt(st: St) {
        viewModelScope.launch(Dispatchers.IO) {
            fR.insertSt(st)
        }
    }


    private fun List<DA>.toItemDAs(): List<ItemDA> {
        return this.map { app ->
            ItemDA(
                app,
                handleDA,
                R.layout.item_da
            )
        }
    }

    fun getList(das: List<DA>, query: String, sort: Sort): List<ItemDA> {
        return das.search(query, ::search)
            .sort(sort(sort))
            .toItemDAs()
    }


    // 搜索
    private fun search(da: DA) = "${da.info.name}${da.info.packageName}"


    // 排序
    private fun sort(sort: Sort): java.util.Comparator<DA> {
        return when (sort) {
            Sort.Count -> sortByCount()
            Sort.CountTime -> sortByCountTime()
            Sort.Name -> sortByName()
        }
    }

    private fun sortByName(): java.util.Comparator<DA> {
        return Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.info.name, s2.info.name)
        }
    }

    private fun sortByCount(): Comparator<DA> {
        return compareByDescending { it.info.count }
    }

    private fun sortByCountTime(): Comparator<DA> {
        return compareByDescending { it.info.countTime }
    }
}