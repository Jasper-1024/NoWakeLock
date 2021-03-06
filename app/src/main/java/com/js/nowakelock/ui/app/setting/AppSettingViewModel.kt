package com.js.nowakelock.ui.app.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.repository.appinforepository.AppInfoRepository
import kotlinx.coroutines.launch

class AppSettingViewModel(
    private var packageName: String = "",
    private var appInfoRepository: AppInfoRepository
) : ViewModel() {

    var appInfoSt: LiveData<AppInfoSt> = appInfoRepository.getAppSetting(packageName).asLiveData()

    fun saveAppSetting(appInfoSt: AppInfoSt?) {
        viewModelScope.launch {
//            LogUtil.d("test1", "$appInfoSt}")
            val tmp = appInfoSt ?: AppInfoSt(packageName)
            appInfoRepository.saveAppSetting(tmp)
        }

    }

}