package com.js.nowakelock.ui.infofragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.clipboardCopy
import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.inforepository.InfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class InfoViewModel(
    val name: String,
    val packageName: String,
    private var infoRepository: InfoRepository
) : ViewModel() {

    var item: LiveData<Item> = infoRepository.getItem(name).map {
        val tmp = infoRepository.getItem_st(name)
        it.flag.set(tmp.flag)
        it.allowTimeinterval = tmp.allowTimeinterval
        it
    }.asLiveData()


    var appInfo: LiveData<AppInfo> = infoRepository.getAppInfo(packageName).asLiveData()


    fun saveST(item: Item) = viewModelScope.launch(Dispatchers.IO) {
        infoRepository.setItem_st(
            ItemSt().apply {
                name = item.name
                flag = item.flag.get()
                allowTimeinterval = item.allowTimeinterval
            }
        )
    }


    fun copy(str: String): Boolean {
        return clipboardCopy(str)
    }
}