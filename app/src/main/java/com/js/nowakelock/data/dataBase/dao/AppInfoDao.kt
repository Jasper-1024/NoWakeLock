package com.js.nowakelock.data.dataBase.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao : BaseDao<AppInfo> {

    @Query("select * from appInfo")
    fun loadAppInfos(): Flow<List<AppInfo>>

    @Query("select * from appInfo where packageName = :packageName")
    suspend fun loadAppInfo(packageName: String): AppInfo
}