package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfoSt

interface AppInfoRepository {
    fun getAppLists(): LiveData<List<AppInfo>>
    suspend fun syncAppInfos()

    fun getAppSetting(packageName: String): LiveData<AppInfoSt>
    suspend fun saveAppSetting(appinfoSt: AppInfoSt)
}