package com.js.nowakelock.ui.fragment.fbase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.repository.frepository.FRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

class FViewModel(
    packageName: String = "",
    private var FRepository: FRepository
) : ViewModel() {
    var list: LiveData<List<Item>> =
        if (packageName == "") FRepository.getLists().asLiveData()
        else FRepository.getLists(packageName).asLiveData()


    suspend fun list(items: List<Item>, catch: cache): List<Item> =
        withContext(Dispatchers.Default) {
            return@withContext items
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    private fun search(item: Item) = item.info.name

    // get sort method
    private fun sort(sort: Int): java.util.Comparator<Item> {
        return when (sort) {
            1 -> Comparator { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.info.name, s2.info.name)
            }
            2 -> compareByDescending { it.info.count - it.info.blockCount }
            3 -> compareByDescending { it.info.countTime - it.info.blockCountTime }
            else -> Comparator { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.info.name, s2.info.name)
            }
        }
    }

    //save st
    fun saveST(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        item.st?.let {
//            it.flag = item.stFlag.get()
            FRepository.setItemSt(it)
        }
    }

    fun setItemStsFlag(itemSts: List<String>, flag: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            val tmp = itemSts.map {
                FRepository.getItemSt(it).apply { this.flag = flag }
            }
            FRepository.setItemSt(tmp)
        }
    }

//    fun setItemStsAti(itemSts: List<String>, ati: Long) {
//        viewModelScope.launch(Dispatchers.Default) {
//            itemSts.forEach {
//                FRepository.setItemSt(
//                    FRepository.getItemSt(it).apply { this.allowTimeinterval = ati })
//            }
//        }
//    }

    //    // get all ItemInfo flag
//    suspend fun List<Item>.flag(): List<Item> {
//        return this.map {
//            val tmp = FRepository.getItemSt(it.itemInfo.name)
//            it.itemInfo.flag.set(tmp.flag)
//            it..itallowTimeinterval = tmp.allowTimeinterval
//            it
//        }
//    }
}