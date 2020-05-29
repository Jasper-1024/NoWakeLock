package com.js.nowakelock.data.repository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.AlarmDao
import com.js.nowakelock.data.db.entity.AlarmSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IAlarmR(private val alarmDao: AlarmDao) : FRepository {
    override fun getLists(): Flow<List<Item>> {
        return alarmDao.loadAlarms()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return alarmDao.loadAlarms(packageName)
    }

    override suspend fun sync(pN: String) {
    }

    override suspend fun getItem_st(name: String): ItemSt = withContext(Dispatchers.IO) {
        return@withContext alarmDao.loadAlarmSt(name) ?: AlarmSt(name)
    }

    override suspend fun setItem_st(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        alarmDao.insert(
            AlarmSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        )
    }
}