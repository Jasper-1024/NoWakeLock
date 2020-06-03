package com.js.nowakelock.ui.infofragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.repository.inforepository.InfoRepository

class InfoViewModel(
    val name: String,
    val packageName: String,
    private var infoRepository: InfoRepository
) : ViewModel() {
    var item: LiveData<Item> = infoRepository.getItem(name).asLiveData()

    var appInfo: LiveData<AppInfo> = infoRepository.getAppInfo(packageName).asLiveData()

    var test = "123"
}