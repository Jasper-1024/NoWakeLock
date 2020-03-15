package com.js.nowakelock.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.db.entity.WakeLock

@Dao
interface WakeLockDao {
    @Query("select * from wakeLock where WakeLock_packageName = :packageName")
    suspend fun loadAllWakeLocks(packageName: String): List<WakeLock>

    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    suspend fun loadWakeLock(wakelockName: String): WakeLock

    @Query("update wakeLock set count = count+1 where wakeLockName = :wakelockName")
    suspend fun upCount(wakelockName: String)

    @Query("update wakeLock set blockCount = blockCount+1 where wakeLockName = :wakelockName")
    suspend fun upBlockCount(wakelockName: String)

    @Query("update wakeLock set count = 0 where wakeLockName = :wakelockName")
    suspend fun rstCount(wakelockName: String)

    @Query("update wakeLock set blockCount = 0 where wakeLockName = :wakelockName")
    suspend fun rstBlockCount(wakelockName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wakeLocks: MutableCollection<WakeLock>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wakeLock: WakeLock)

    @Delete
    suspend fun deleteAll(wakeLocks: MutableCollection<WakeLock>)

    @Delete
    suspend fun delete(wakeLock: WakeLock)
}