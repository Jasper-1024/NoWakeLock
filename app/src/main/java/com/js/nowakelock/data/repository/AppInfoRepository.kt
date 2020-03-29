package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.AppInfo

interface AppInfoRepository {
    fun getAppLists(): LiveData<List<AppInfo>>
    suspend fun syncAppInfos()
}