package com.js.nowakelock.data.repository.frepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.ServiceDao
import com.js.nowakelock.data.db.entity.ServiceSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IServiceR(private val serviceDao: ServiceDao) :
    FRepository {
    override fun getLists(): Flow<List<Item>> {
        return serviceDao.loadServices()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return serviceDao.loadServices(packageName)
    }

    override suspend fun sync(pN: String) {
    }

    override suspend fun getItem_st(name: String): ItemSt = withContext(Dispatchers.IO) {
        return@withContext serviceDao.loadServiceSt(name)
            ?: ServiceSt(name).apply { serviceDao.insert(this) }
    }

    override suspend fun setItem_st(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        serviceDao.insert(
            ServiceSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        )
    }
}