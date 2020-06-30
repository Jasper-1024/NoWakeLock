package com.js.nowakelock.data.repository.frepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.ServiceDao
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.ServiceSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class IServiceR(private val serviceDao: ServiceDao) :
    FRepository {
    override fun getLists(): Flow<List<Item>> {
        return serviceDao.loadServices().map { items ->
            items.forEach {
                if (it.st == null) {
                    it.st = ServiceSt(
                        name = it.info.name,
                        packageName = it.info.packageName
                    ).apply { serviceDao.insertST(this) }
                }
                it.stFlag.set(it.st!!.flag)
            }
            items
        }
    }

    override fun getLists(packageName: String): Flow<List<Service>> {
        return serviceDao.loadServices(packageName)
    }

    override suspend fun sync(pN: String) {
    }

    override suspend fun getItemSt(name: String): ItemSt = withContext(Dispatchers.IO) {
        return@withContext serviceDao.loadServiceSt(name)
            ?: ServiceSt(name).apply { serviceDao.insertST(this) }
    }

    override suspend fun setItemSt(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        serviceDao.insertST(
            ServiceSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        )
    }

    override suspend fun setItemSt(itemSts: List<ItemSt>) = withContext(Dispatchers.IO) {
        val tmp = itemSts.map { itemSt ->
            ServiceSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        }
        serviceDao.insertST(tmp)
    }
}