package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt

@Dao
interface BackupDao {

    @Query("select * from alarm_st where packageName = :packageName")
    suspend fun loadAlarmSts(packageName: String): List<AlarmSt>

    @Query("select * from service_st where packageName = :packageName")
    fun loadServiceSts(packageName: String): List<ServiceSt>

    @Query("select * from wakeLock_st where packageName = :packageName")
    fun loadWakeLockSts(packageName: String): List<WakeLockSt>

    @Query("select packageName from appInfo")
    suspend fun loadAppNames(): List<String>

    @Query("select * from appInfo_st where packageName_st = :packageName")
    suspend fun loadAppSt(packageName: String): AppInfoSt?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmSt: AlarmSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceSt: ServiceSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wNLock_st: WakeLockSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(appInfo_st: AppInfoSt)
}