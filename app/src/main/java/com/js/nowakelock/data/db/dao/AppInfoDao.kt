package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.AppCount
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.Info
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao : BaseDao<AppInfo> {

    @Transaction
    @Query("select * from appInfo where userId = :userId")
    fun loadAppInfos(userId: Int = 0): Flow<List<AppDA>>

    @Query("select * from appInfo")
    fun loadAppInfosDB(): List<AppInfo>

    @Query("select * from appInfo where packageName = :packageName and userId = :userId")
    suspend fun loadAppInfo(packageName: String, userId: Int = 0): AppInfo

    //    @Query("select count,countTime,blockCount from info where name_info = :name")
    @Query("select * from AppCount")
    suspend fun loadAppCount(): List<AppCount>

//    @Query("select * from appInfo")
//    fun loadAppInfos2(): Flow<List<AppDA>>
}