package com.js.nowakelock.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.js.nowakelock.data.db.entity.*
import kotlinx.coroutines.flow.Flow

interface InfoDao {
    @Query("select * from alarm where alarmName = :alarmName")
    fun loadAlarm(alarmName: String): Flow<Alarm>

    @Query("select * from service where serviceName = :serviceName")
    fun loadService(serviceName: String): Flow<Service>

    @Query("select * from wakeLock where wakeLockName = :wakelockName")
    fun loadWakeLock(wakelockName: String): Flow<WakeLock>

    @Query("select * from appInfo where packageName = :packageName ")
    fun loadAppInfo(packageName: String): Flow<AppInfo>

    /**alarm_st*/
    @Query("select * from alarm_st where alarmName_st = :alarmName")
    fun loadAlarmSt(alarmName: String): AlarmSt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmSt: AlarmSt)

    /**service_st*/
    @Query("select * from service_st where serviceName_st = :serviceName")
    fun loadServiceSt(serviceName: String): ServiceSt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceSt: ServiceSt)

    /**wakelock_st*/
    @Query("select * from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLockSt(wakelockName: String): WakeLockSt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wNLock_st: WakeLockSt)
}