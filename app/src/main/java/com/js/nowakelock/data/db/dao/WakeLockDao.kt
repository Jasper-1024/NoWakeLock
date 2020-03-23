package com.js.nowakelock.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.data.db.entity.WakeLock

@Dao
interface WakeLockDao {

    @Query("select * from wakeLock")
    fun loadAllWakeLocks(): List<WakeLock>

    @Query("select * from wakeLock where wakeLock_packageName = :packageName")
    fun loadAllWakeLocks(packageName: String): LiveData<List<WakeLock>>

    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    suspend fun loadWakeLock(wakelockName: String): WakeLock

    @Query("select packageName from appInfo")
    fun loadPackageNames(): LiveData<List<String>>

    @Query("update appInfo set count = (select sum(wakeLock_count) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoCount(packageName: String)

    @Query("update appInfo set blockCount = (select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoBlockCount(packageName: String)

//    @Query("select * from wakeLock where WakeLock_packageName = :packageName")
//    suspend fun loadAllWakeLockByPn(packageName: String): List<WakeLock>
//
//    @Query("select * from wakeLock")
//    fun loadAllWakeLock(): List<WakeLock>

//    @Query("update wakeLock set WakeLock_count = WakeLock_count+1 where wakeLockName = :wakelockName")
//    suspend fun upCount(wakelockName: String)
//
//    @Query("update wakeLock set blockCount = blockCount+1 where wakeLockName = :wakelockName")
//    suspend fun upBlockCount(wakelockName: String)
//
//    @Query("update wakeLock set WakeLock_count = 0 where wakeLockName = :wakelockName")
//    suspend fun rstCount(wakelockName: String)
//
//    @Query("update wakeLock set blockCount = 0 where wakeLockName = :wakelockName")
//    suspend fun rstBlockCount(wakelockName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wakeLocks: MutableCollection<WakeLock>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wakeLock: WakeLock)

    @Delete
    suspend fun deleteAll(wakeLocks: MutableCollection<WakeLock>)

    @Delete
    suspend fun delete(wakeLock: WakeLock)
}