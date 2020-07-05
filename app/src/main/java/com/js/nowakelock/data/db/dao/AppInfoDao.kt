package com.js.nowakelock.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfoSt
import kotlinx.coroutines.flow.Flow

@Dao
interface AppInfoDao {

    /**for applist fragment*/
    @Query("select * from appInfo")
    fun loadAppInfos(): LiveData<List<AppInfo>>

    /** applist_Repository*/
    @Query("select * from appInfo")
    fun loadAllAppInfos(): List<AppInfo>

    @Query("select * from appInfo where packageName = :packageName")
    suspend fun loadAppInfo(packageName: String): AppInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfos: List<AppInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfos: MutableCollection<AppInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfo)

    @Delete
    suspend fun deleteAll(appInfos: MutableCollection<AppInfo>)

    @Delete
    suspend fun delete(appInfo: AppInfo)

    /**for appinfo_st*/
    @Query("select * from appInfo_st where packageName_st = :packageName")
    fun loadAppSetting(packageName: String): Flow<AppInfoSt?>

    @Query("select * from appInfo_st")
    fun loadAppSettings(): List<AppInfoSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo_st: AppInfoSt)

    @Delete
    suspend fun delete(appInfo_st: AppInfoSt)


//    @Query("select * from appInfo where packageName = :packageName")
//    suspend fun loadAppInfo(packageName: String): AppInfo

    @Query("select packageName from appInfo")
    suspend fun loadPackageNames(): List<String>

    @Transaction
    @Query(
        """
        update appInfo set count = (
            ifnull((select sum(wakeLock_count) from wakeLock where wakeLock_packageName = :packageName),0)
            - ifnull((select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName),0)
            + ifnull((select sum(alarm_count) from alarm where alarm_packageName = :packageName) ,0)
            - ifnull((select sum(alarm_blockCount) from alarm where alarm_packageName = :packageName),0)
        )
        where packageName = :packageName
        """
    )
    suspend fun updateAppInfoCount(packageName: String)
//
//    @Query("update appInfo set blockCount = (select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
//    suspend fun updateAppInfoBlockCount(packageName: String)
}