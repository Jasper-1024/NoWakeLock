package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.dao.AlarmDao
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.Alarm_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class mAlarmRepository(private val alarmDao: AlarmDao) : AlarmRepository {

    /**no packageName*/
    override fun getAlarms(): LiveData<List<Alarm>> = alarmDao.loadAlarms()

    /**packageName*/
    override fun getAlarms(packageName: String): LiveData<List<Alarm>> =
        alarmDao.loadAlarms(packageName)

    /**not yet*/
    override suspend fun sync(pN: String) {
        TODO("NOE YET")
    }

    override suspend fun getAlarm_st(aN: String): Alarm_st = withContext(Dispatchers.IO) {
        return@withContext alarmDao.loadAlarm_st(aN) ?: Alarm_st(aN)
    }

    override suspend fun setAlarm_st(alarmSt: Alarm_st) = withContext(Dispatchers.IO) {
        alarmDao.insert(alarmSt)
    }
}