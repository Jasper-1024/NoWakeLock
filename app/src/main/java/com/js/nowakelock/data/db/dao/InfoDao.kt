package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.js.nowakelock.data.db.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InfoDao {
    @Query("select * from alarm where alarmName = :alarmName")
    fun loadAlarm(alarmName: String): Flow<AlarmInfo>

    @Query("select * from service where serviceName = :serviceName")
    fun loadService(serviceName: String): Flow<ServiceInfo>

    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    fun loadWakeLock(wakelockName: String): Flow<WakeLockInfo>

    @Query("select * from appInfo where packageName = :packageName ")
    fun loadAppInfo(packageName: String): Flow<AppInfo>

    /**alarm_st*/
    @Query("select * from alarm_st where alarmName_st = :alarmName")
    fun loadAlarmSt(alarmName: String): Flow<AlarmSt?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmSt: AlarmSt)

    /**service_st*/
    @Query("select * from service_st where serviceName_st = :serviceName")
    fun loadServiceSt(serviceName: String): Flow<ServiceSt?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceSt: ServiceSt)

    /**wakelock_st*/
    @Query("select * from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLockSt(wakelockName: String): Flow<WakeLockSt?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wNLock_st: WakeLockSt)
}