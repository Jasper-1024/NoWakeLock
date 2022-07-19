package com.js.nowakelock.ui.appDa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.SPTools
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.AppSt
import com.js.nowakelock.data.repository.appda.AppDaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class AppDaViewModel(val packageName: String, val userId: Int) : ViewModel(), KoinComponent {
    private val appDaR: AppDaRepo by inject(named("AppDaR"))

    var appDa = appDaR.getAppDa(packageName, userId).asLiveData()

    fun saveAppSt(appSt: AppSt) {
        viewModelScope.launch(Dispatchers.IO) {
            appDaR.setAppSt(appSt)
        }
    }

    fun syncAppSt() {
        viewModelScope.launch(Dispatchers.IO) {
            appDaR.getAppSt(packageName, userId = 0).collect {
                saveAppStSP(it)
            }
        }
    }

    private fun saveAppStSP(appSt: AppSt) {
        SPTools.setSet("${Type.Wakelock}_${appSt.packageName}_rE", appSt.rE_Wakelock)
        SPTools.setSet("${Type.Alarm}_${appSt.packageName}_rE", appSt.rE_Alarm)
    }

}