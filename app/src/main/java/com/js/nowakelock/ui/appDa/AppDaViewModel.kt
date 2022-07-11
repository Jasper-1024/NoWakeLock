package com.js.nowakelock.ui.appDa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.entity.AppSt
import com.js.nowakelock.data.repository.appda.AppDaRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class AppDaViewModel(packageName: String) : ViewModel(), KoinComponent {
    private val appDaR: AppDaRepo by inject(named("AppDaR"))

    var appDa = appDaR.getAppDa(packageName).asLiveData()

    fun saveAppSt(appSt: AppSt) {
        viewModelScope.launch(Dispatchers.IO) {
            appDaR.setAppSt(appSt)
        }
    }
}