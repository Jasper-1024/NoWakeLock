package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.WakeLockInfo
import com.js.nowakelock.data.db.entity.WakeLockSt
import com.js.nowakelock.data.db.entity.Wakelock
import kotlinx.coroutines.flow.Flow

@Dao
interface WakeLockDao {

    /**for wakelock fragment*/
    @Transaction
    @Query("select * from wakeLock")
    fun loadWakeLocks(): Flow<List<Wakelock>>

    @Transaction
    @Query("select * from wakeLock where wakeLock_packageName = :packageName")
    fun loadWakeLocks(packageName: String): Flow<List<Wakelock>>

    /**for BroadcastReceiver clear db*/
    @Query("select * from wakeLock")
    suspend fun loadAllWakeLocks(): List<WakeLockInfo>

    /**for ProviderHandler,save service to db*/
    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    suspend fun loadWakeLock(wakelockName: String): WakeLockInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wakeLockInfo: WakeLockInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wakeLockInfos: List<WakeLockInfo>)

    @Delete
    suspend fun deleteAll(wakeLockInfos: MutableCollection<WakeLockInfo>)

    @Delete
    suspend fun delete(wakeLockInfo: WakeLockInfo)

    /**wakelock_st_Name*/
    @Query("select wakeLockName_st from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLockStName(wakelockName: String): String?

    /**wakelock_st*/
    @Query("select * from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLockSt(wakelockName: String): WakeLockSt?

    /**for ContentProvider*/
    @Query("select * from wakeLock_st")
    fun loadWakeLockSt(): List<WakeLockSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(wakeLockSt: WakeLockSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(wakeLockSts: List<WakeLockSt>)

//    @Query("select COUNT(*) from wakeLock where wakeLock_packageName = :packageName")
//    suspend fun countWakeLocks(packageName: String): Int
//
//    @Query("update appInfo set count = (select sum(wakeLock_count) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
//    suspend fun updateAppInfoCount(packageName: String)
//
//    @Query("update appInfo set blockCount = (select sum(wakeLock_blockCount) from wakeLock where wakeLock_packageName = :packageName) where packageName = :packageName")
//    suspend fun updateAppInfoBlockCount(packageName: String)

//    @Query("select * from wakeLock where WakeLock_packageName = :packageName")
//    suspend fun loadAllWakeLockByPn(packageName: String): List<WakeLockInfo>
//
//    @Query("select * from wakeLock")
//    fun loadAllWakeLock(): List<WakeLockInfo>

    //Determine if it exists
//    @Query("select wakeLockName from wakeLock where wakeLockName = :wakelockName")
//    suspend fun loadwakelockName(wakelockName: String): String

//    @Query("select flag from wakeLock where wakeLockName = :wakelockName")
//    suspend fun loadFlag(wakelockName: String): Boolean

//    @Query("update wakeLock set WakeLock_count = WakeLock_count+1 where wakeLockName = :wakelockName")
//    fun upCount(wakelockName: String)
//
//    @Query("update appInfo set count = count+1 where packageName = :packageName")
//    fun upCountP(packageName: String)
//
//    @Query("update wakeLock set wakeLock_blockCount = wakeLock_blockCount+1 where wakeLockName = :wakelockName")
//    fun upBlockCount(wakelockName: String)
//
//    @Query("update appInfo set blockCount = blockCount+1 where packageName = :packageName")
//    fun upBlockCountP(packageName: String)
//
//    @Query("update wakeLock set WakeLock_count = 0 where wakeLockName = :wakelockName")
//    suspend fun rstCount(wakelockName: String)
//
//    @Query("update wakeLock set wakeLock_blockCount = 0 where wakeLockName = :wakelockName")
//    suspend fun rstBlockCount(wakelockName: String)


//
//
//    /**
//     * whether app install or not
//     *
//     * @return A [String] of packageName
//     */
//    @Query("select packageName from appInfo where packageName = :packageName")
//    fun loadPackageName(packageName: String): String?
//
//    /**
//     * whether wakeLockName exit or not
//     *
//     * @return A [String] of wakeLockName
//     */
//    @Query("select wakeLockName from wakeLock where wakeLockName = :wakelockName")
//    fun loadWakelockName(wakelockName: String): String?
//
//    /**
//     * insert all wakeLocks
//     *
//     * @return null
//     */
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(wakeLocks: MutableCollection<WakeLockInfo>)
}