package com.js.nowakelock.data.repository.appdas

import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

interface AppDasRepo {
    fun getAppDAs(): Flow<List<AppDA>>
    suspend fun getAppInfo(packageName: String): AppInfo
    suspend fun syncAppInfos()
    suspend fun syncInfos()
}