package com.js.nowakelock.data.repository

import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.base.Item_st
import com.js.nowakelock.data.db.dao.AlarmDao
import com.js.nowakelock.data.db.entity.Alarm_st
import com.js.nowakelock.data.db.entity.WakeLock_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class mAlarmR(private val alarmDao: AlarmDao) : FRepository {
    override fun getLists(): Flow<List<Item>> {
        return alarmDao.loadAlarms()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return alarmDao.loadAlarms(packageName)
    }

    override suspend fun sync(pN: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getItem_st(name: String): Item_st = withContext(Dispatchers.IO) {
        return@withContext alarmDao.loadAlarm_st(name) ?: Alarm_st(name)
    }

    override suspend fun setItem_st(itemSt: Item_st) = withContext(Dispatchers.IO) {
        alarmDao.insert(
            Alarm_st(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        )
    }
}