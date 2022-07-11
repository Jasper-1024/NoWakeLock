package com.js.nowakelock.data.repository.appda

import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppSt
import kotlinx.coroutines.flow.Flow

interface AppDaRepo {
    fun getAppDa(packageName: String): Flow<AppDA>
    suspend fun setAppSt(appSt: AppSt)
}