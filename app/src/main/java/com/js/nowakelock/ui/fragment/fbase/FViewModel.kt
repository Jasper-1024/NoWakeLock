package com.js.nowakelock.ui.fragment.fbase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.base.Item_st
import com.js.nowakelock.data.repository.FRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

class FViewModel(
    packageName: String = "",
    var FRepository: FRepository
) : ViewModel() {
    var list: LiveData<List<Item>> =
        if (packageName == "") FRepository.getLists().asLiveData()
        else FRepository.getLists(packageName).asLiveData()


    suspend fun list(items: List<Item>, catch: cache): List<Item> =
        withContext(Dispatchers.Default) {
            return@withContext items.flag()
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    // get all Item flag
    suspend fun List<Item>.flag(): List<Item> {
        return this.map {
            val tmp = FRepository.getItem_st(it.name)
            it.flag.set(tmp.flag)
            it.allowTimeinterval = tmp.allowTimeinterval
            it
        }
    }

    fun search(wakeLock: Item) = wakeLock.name

    // get sort method
    fun sort(sort: Int): java.util.Comparator<Item> {
        return when (sort) {
            1 -> Comparator<Item> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.name, s2.name)
            }
            2 -> compareByDescending<Item> { it.count }
            3 -> compareByDescending<Item> { it.countTime }
            else -> Comparator<Item> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.name, s2.name)
            }
        }
    }

    //save st
    fun saveST(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        FRepository.setItem_st(
            Item_st().apply {
                name = item.name
                flag = item.flag.get()
                allowTimeinterval = item.allowTimeinterval
            }
        )
    }
}