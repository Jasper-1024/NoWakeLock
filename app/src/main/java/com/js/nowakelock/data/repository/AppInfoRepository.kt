package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfo_st

interface AppInfoRepository {
    fun getAppLists(): LiveData<List<AppInfo>>
    suspend fun syncAppInfos()

    fun getAppSetting(packageName: String): LiveData<AppInfo_st>
    suspend fun saveAppSetting(appinfoSt: AppInfo_st)
}