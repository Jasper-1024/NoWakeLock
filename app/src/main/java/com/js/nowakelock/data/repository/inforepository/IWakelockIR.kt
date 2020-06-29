package com.js.nowakelock.data.repository.inforepository

import com.js.nowakelock.data.db.base.ItemInfo
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.WakeLockSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class IWakelockIR(private val infoDao: InfoDao) : InfoRepository {
    override fun getItem(name: String): Flow<ItemInfo> {
        return infoDao.loadWakeLock(name)
    }

    override fun getAppInfo(packageName: String): Flow<AppInfo> {
        return infoDao.loadAppInfo(packageName)
    }

    override fun getItemSt(name: String): Flow<ItemSt> {
        return infoDao.loadWakeLockSt(name)
    }

    override suspend fun setItemSt(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        val tmp = WakeLockSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        infoDao.insert(tmp)
    }
}