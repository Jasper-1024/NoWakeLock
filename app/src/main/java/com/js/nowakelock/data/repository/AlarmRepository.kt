package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.Alarm_st

interface AlarmRepository {
    fun getAlarms(): LiveData<List<Alarm>>
    fun getAlarms(packageName: String): LiveData<List<Alarm>>

    suspend fun sync(pN: String)

    suspend fun getAlarm_st(aN: String): Alarm_st
    suspend fun setAlarm_st(alarmSt: Alarm_st)

}