package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt

@Dao
interface BackupDao {

    @Query("select alarmName from alarm where alarm_packageName = :packageName")
    suspend fun loadAlarmNames(packageName: String): List<String>

    @Query("select * from alarm_st where alarmName_st = :alarmName")
    suspend fun loadAlarmSt(alarmName: String): AlarmSt?

    @Query("select serviceName from service where service_packageName = :packageName")
    suspend fun loadServiceNames(packageName: String): List<String>

    @Query("select * from service_st where serviceName_st = :serviceName")
    fun loadServiceSt(serviceName: String): ServiceSt?

    @Query("select wakeLockName from wakeLock where wakeLock_packageName = :packageName")
    fun loadWakeLockNames(packageName: String): List<String>

    @Query("select * from wakeLock_st where wakeLockName_st = :wakelockName")
    fun loadWakeLockSt(wakelockName: String): WakeLockSt?

    @Query("select packageName from appInfo")
    suspend fun loadAppNames(): List<String>

    @Query("select * from appInfo_st where packageName_st = :packageName")
    suspend fun loadAppSt(packageName: String): AppInfoSt
}