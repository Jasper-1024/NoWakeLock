package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.AlarmInfo
import com.js.nowakelock.data.db.entity.AlarmSt
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    /**for alarm fragment,no packageName*/
    @Transaction
    @Query("select * from alarm")
    fun loadAlarms(): Flow<List<Alarm>>

    /**for alarm fragment*/
    @Transaction
    @Query("select * from alarm where alarm_packageName = :packageName")
    fun loadAlarms(packageName: String): Flow<List<Alarm>>

    /**for ProviderHandler,save alarm to db*/
    @Query("select * from alarm where alarmName = :alarmName")
    suspend fun loadAlarm(alarmName: String): AlarmInfo?

    /**for BroadcastReceiver clear db*/
    @Query("select * from alarm")
    suspend fun loadAllAlarms(): List<AlarmInfo>

    /**insert*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmInfo: AlarmInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(alarmInfoS: List<AlarmInfo>)

    /**delete*/
    @Delete
    suspend fun delete(alarmInfo: AlarmInfo)

    @Delete
    suspend fun delete(alarmInfos: List<AlarmInfo>)

    /**alarm_st_Name whether alarm_st exist*/
    @Query("select alarmName_st from alarm_st where alarmName_st = :alarmName")
    fun loadAlarmStName(alarmName: String): String?

    /**alarm_st*/
    @Query("select * from alarm_st where alarmName_st = :alarmName")
    fun loadAlarmSt(alarmName: String): AlarmSt?

    /**for ContentProvider*/
    @Query("select * from alarm_st")
    fun loadAlarmSt(): List<AlarmSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(alarmSt: AlarmSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(alarmSts: List<AlarmSt>)
}