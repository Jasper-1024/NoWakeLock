package com.js.nowakelock.data.repository.appinforepository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfoSt
import kotlinx.coroutines.flow.Flow

interface AppInfoRepository {
    fun getAppLists(): LiveData<List<AppInfo>>
    suspend fun syncAppInfos()

    fun getAppSetting(packageName: String): Flow<AppInfoSt>
    suspend fun saveAppSetting(appinfoSt: AppInfoSt)
}