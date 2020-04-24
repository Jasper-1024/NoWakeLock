package com.js.nowakelock.data.db.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st

@Dao
interface WakeLockDao {

    @Query("select * from wakeLock")
    fun loadWakeLocks(): LiveData<List<WakeLock>>

    @Query("select * from wakeLock where wakeLock_packageName = :packageName")
    fun loadWakeLocks(packageName: String): LiveData<List<WakeLock>>

    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    suspend fun loadWakeLock(wakelockName: String): WakeLock?

//    @Query("select packageName from appInfo")
//    fun loadPackageNames(): LiveData<List<String>>

    @Query("select COUNT(*) from wakeLock where wakeLock_packageName = :packageName")
    suspend fun countWakeLocks(packageName: String): Int

    @Query("update appInfo set count = (select sum(wakeLock_count) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoCount(packageName: String)

    @Query("update appInfo set blockCount = (select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
    suspend fun updateAppInfoBlockCount(packageName: String)

//    @Query("select * from wakeLock where WakeLock_packageName = :packageName")
//    suspend fun loadAllWakeLockByPn(packageName: String): List<WakeLock>
//
//    @Query("select * from wakeLock")
//    fun loadAllWakeLock(): List<WakeLock>

    //Determine if it exists
    @Query("select wakeLockName from wakeLock where wakeLockName = :wakelockName")
    suspend fun loadwakelockName(wakelockName: String): String

//    @Query("select flag from wakeLock where wakeLockName = :wakelockName")
//    suspend fun loadFlag(wakelockName: String): Boolean

    @Query("update wakeLock set WakeLock_count = WakeLock_count+1 where wakeLockName = :wakelockName")
    fun upCount(wakelockName: String)

    @Query("update appInfo set count = count+1 where packageName = :packageName")
    fun upCountP(packageName: String)

    @Query("update wakeLock set wakeLock_blockCount = wakeLock_blockCount+1 where wakeLockName = :wakelockName")
    fun upBlockCount(wakelockName: String)

    @Query("update appInfo set blockCount = blockCount+1 where packageName = :packageName")
    fun upBlockCountP(packageName: String)

    @Query("update wakeLock set WakeLock_count = 0 where wakeLockName = :wakelockName")
    suspend fun rstCount(wakelockName: String)

    @Query("update wakeLock set wakeLock_blockCount = 0 where wakeLockName = :wakelockName")
    suspend fun rstBlockCount(wakelockName: String)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wakeLock: WakeLock)

    @Delete
    suspend fun deleteAll(wakeLocks: MutableCollection<WakeLock>)

    @Delete
    suspend fun delete(wakeLock: WakeLock)

//    /**
//     * Select wakelock flag
//     *
//     * @return A [Cursor] of wakelock flag (default true)
//     */
//    @Query("select flag from wakeLock where wakeLockName = :wakelockName")
//    fun loadFlag(wakelockName: String): Cursor?

    /**
     * whether app install or not
     *
     * @return A [String] of packageName
     */
    @Query("select packageName from appInfo where packageName = :packageName")
    fun loadPackageName(packageName: String): String?

    /**
     * whether wakeLockName exit or not
     *
     * @return A [String] of wakeLockName
     */
    @Query("select wakeLockName from wakeLock where wakeLockName = :wakelockName")
    fun loadWakelockName(wakelockName: String): String?

    /**
     * insert all wakeLocks
     *
     * @return null
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(wakeLocks: MutableCollection<WakeLock>)

    /**
     * get wakeLock_st
     *
     * @return wakeLock_st
     */
    @Query("select * from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLock_st(wakelockName: String): WakeLock_st?

    @Query("select * from wakeLock_st")
    fun loadWakeLock_st(): List<WakeLock_st>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wNLock_st: WakeLock_st)
}