package com.js.nowakelock.ui.app.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.AppInfo_st
import com.js.nowakelock.data.db.entity.WakeLock_st
import com.js.nowakelock.data.repository.AppInfoRepository
import kotlinx.coroutines.launch

class AppSettingViewModel(
    private var packageName: String = "",
    private var appInfoRepository: AppInfoRepository
) : ViewModel() {

    var appInfoSt: LiveData<AppInfo_st> = appInfoRepository.getAppSetting(packageName)

    fun saveAppSetting(appInfoSt: AppInfo_st?) {
        viewModelScope.launch {
//            LogUtil.d("test1", "$appInfoSt}")
            val tmp = appInfoSt ?: AppInfo_st(packageName)
            appInfoRepository.saveAppSetting(tmp)
        }

    }

}