package com.js.nowakelock.ui.fragment.fbase

import androidx.lifecycle.*
import com.js.nowakelock.base.SPTools
import com.js.nowakelock.base.clipboardCopy
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.data.repository.das.FR
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
            fR.resumeSt2Info()
            val infos = fR.getCPInfos(packageName)
            fR.insertInfos(infos)
        }
    }

    fun setSt(st: St) {
        viewModelScope.launch(Dispatchers.IO) {
            fR.insertSt(st)
        }
    }

    fun syncSt() {
        viewModelScope.launch(Dispatchers.IO) {
            fR.getSts().collect { list ->
//                LogUtil.d("sync", "${list.size}")
                list.map {
                    saveSt(it)
                }
            }
        }
    }

    fun copy(str: String): Boolean {
        return clipboardCopy(str)
    }


    private fun List<DA>.toItemDAs(layout: Int): List<ItemDA> {
        return this.map { app ->
            ItemDA(app, handleDA, layout)
        }
    }

    fun getList(das: List<DA>, query: String, sort: Sort, layout: Int): List<ItemDA> {
        return das.search(query, ::search)
            .sort(sort(sort))
            .toItemDAs(layout)
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

    private fun saveSt(st: St) {
        SPTools.setBoolean("${st.name}_${st.type}_${st.packageName}_flag", st.flag)
        SPTools.setLong(
            "${st.name}_${st.type}_${st.packageName}_aTI",
            st.allowTimeInterval
        )

//        LogUtil.d("Nowakelcok", "${st.name}_${st.type}_${st.packageName}_flag: ${st.flag}")
//        LogUtil.d(
//            "Nowakelcok",
//            "${st.name}_${st.type}_${st.packageName}_aTI: ${st.allowTimeInterval}"
//        )
    }
}