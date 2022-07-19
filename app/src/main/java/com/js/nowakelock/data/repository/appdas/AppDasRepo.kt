package com.js.nowakelock.data.repository.appdas

import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

interface AppDasRepo {
    fun getAppDAs(userId: Int): Flow<List<AppDA>>
    suspend fun getAppInfo(packageName: String, useId: Int): AppInfo
    suspend fun syncAppInfos(userId: Int)
    suspend fun syncInfos()
}