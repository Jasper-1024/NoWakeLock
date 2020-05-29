package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.AlarmSt
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    /**for alarm fragment,no packageName*/
    @Query("select * from alarm")
    fun loadAlarms(): Flow<List<Alarm>>

    /**for alarm fragment*/
    @Query("select * from alarm where alarm_packageName = :packageName")
    fun loadAlarms(packageName: String): Flow<List<Alarm>>

    /**for ProviderHandler,save alarm to db*/
    @Query("select * from alarm where alarmName = :alarmName")
    suspend fun loadAlarm(alarmName: String): Alarm?

    /**for BroadcastReceiver clear db*/
    @Query("select * from alarm")
    suspend fun loadAllAlarms(): List<Alarm>

    /**insert*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarm: Alarm)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarms: List<Alarm>)

    /**delete*/
    @Delete
    suspend fun delete(alarm: Alarm)

    @Delete
    suspend fun delete(alarms: List<Alarm>)

    /**alarm_st*/
    @Query("select * from alarm_st where alarmName_st = :alarmName")
    fun loadAlarmSt(alarmName: String): AlarmSt?

    /**for ContentProvider*/
    @Query("select * from alarm_st")
    fun loadAlarmSt(): List<AlarmSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmSt: AlarmSt)
}