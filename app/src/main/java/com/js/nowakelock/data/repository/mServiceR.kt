package com.js.nowakelock.data.repository

import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.base.Item_st
import com.js.nowakelock.data.db.dao.ServiceDao
import com.js.nowakelock.data.db.entity.Alarm_st
import com.js.nowakelock.data.db.entity.Service_st
import com.js.nowakelock.data.db.entity.WakeLock_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class mServiceR(private val serviceDao: ServiceDao) : FRepository {
    override fun getLists(): Flow<List<Item>> {
        return serviceDao.loadServices()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return serviceDao.loadServices(packageName)
    }

    override suspend fun sync(pN: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getItem_st(name: String): Item_st = withContext(Dispatchers.IO) {
        return@withContext serviceDao.loadService_st(name) ?: Service_st(name)
    }

    override suspend fun setItem_st(itemSt: Item_st) = withContext(Dispatchers.IO) {
        serviceDao.insert(
            Service_st(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        )
    }
}