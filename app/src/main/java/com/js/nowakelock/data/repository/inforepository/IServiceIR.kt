package com.js.nowakelock.data.repository.inforepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.ServiceSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IServiceIR(private val infoDao: InfoDao) : InfoRepository {
    override fun getItem(name: String): Flow<Item> {
        return infoDao.loadService(name)
    }

    override fun getAppInfo(packageName: String): Flow<AppInfo> {
        return infoDao.loadAppInfo(packageName)
    }

    override fun getItemSt(name: String): Flow<ItemSt> {
        return infoDao.loadServiceSt(name)
    }

    override suspend fun setItemSt(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        val tmp = ServiceSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        infoDao.insert(tmp)
    }
}