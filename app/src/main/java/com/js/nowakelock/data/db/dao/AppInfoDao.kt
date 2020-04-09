package com.js.nowakelock.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.data.db.entity.AppInfo

@Dao
interface AppInfoDao {

    @Query("select * from appInfo")
    fun loadAppInfos(): LiveData<List<AppInfo>>

    @Query("select * from appInfo")
    fun loadAllAppInfos(): List<AppInfo>

    @Query("select * from appInfo where packageName = :packageName")
    suspend fun loadAppInfo(packageName: String): AppInfo

    @Query("select wakeLock_packageName from wakeLock")
    suspend fun loadWLPackageNames(): List<String>

    @Query("update appInfo set count = (select sum(wakeLock_count) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoCount(packageName: String)

    @Query("update appInfo set blockCount = (select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoBlockCount(packageName: String)


//    @Query("select packageName from appInfo")
//    suspend fun loadPackageNames(): List<String>
//
//    @Query("select packageName from appInfo")
//    fun loadPackageNames2(): LiveData<List<String>>
//
//    @Query("update appInfo set count = count+1 where packageName = :packageName")
//    suspend fun upCount(packageName: String)
//
//    @Query("update appInfo set blockCount = blockCount+1 where packageName = :packageName")
//    suspend fun upBlockCount(packageName: String)
//
//    @Query("update appInfo set count = :count where packageName = :packageName")
//    suspend fun upCount(packageName: String, count: Int)
//
//    @Query("update appInfo set blockCount = :blockCount where packageName = :packageName")
//    suspend fun upBlockCount(packageName: String, blockCount: Int)
//
//    @Query("update appInfo set count = 0 where packageName = :packageName")
//    suspend fun rstCount(packageName: String)
//
//    @Query("update appInfo set blockCount = 0 where packageName = :packageName")
//    suspend fun rstBlockCount(packageName: String)

//    /**new test*/
//    @Query("update appInfo set count = (select sum(WakeLock_count) from wakeLock where WakeLock_packageName = :packageName) where packageName = :packageName")
//    suspend fun updateCount(packageName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appInfos: MutableCollection<AppInfo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo: AppInfo)

    @Delete
    suspend fun deleteAll(appInfos: MutableCollection<AppInfo>)

    @Delete
    suspend fun delete(appInfo: AppInfo)
}