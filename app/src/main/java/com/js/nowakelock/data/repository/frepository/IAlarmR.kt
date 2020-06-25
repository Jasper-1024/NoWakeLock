package com.js.nowakelock.data.repository.frepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.AlarmDao
import com.js.nowakelock.data.db.entity.AlarmSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IAlarmR(private val alarmDao: AlarmDao) :
    FRepository {
    override fun getLists(): Flow<List<Item>> {
        return alarmDao.loadAlarms()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return alarmDao.loadAlarms(packageName)
    }

    override suspend fun sync(pN: String) {
    }

    override suspend fun getItemSt(name: String): ItemSt = withContext(Dispatchers.IO) {
        return@withContext alarmDao.loadAlarmSt(name)
            ?: AlarmSt(name).apply { alarmDao.insert(this) }
    }

    override suspend fun setItemSt(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        alarmDao.insert(
            AlarmSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        )
    }
}