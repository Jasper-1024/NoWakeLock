package com.js.nowakelock.data.repository

import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.base.Item_st
import com.js.nowakelock.data.base.Repository
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLock_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class mWakelockR(private var wakeLockDao: WakeLockDao) : Repository {

    override fun getLists(): Flow<List<Item>> {
        return wakeLockDao.WakeLocks()
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return wakeLockDao.WakeLocks(packageName)
    }

    override suspend fun sync(pN: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getItem_st(name: String): Item_st = withContext(Dispatchers.IO) {
        return@withContext wakeLockDao.loadWakeLock_st(name) ?: WakeLock_st(name)
    }

    override suspend fun setItem_st(itemSt: Item_st) = withContext(Dispatchers.IO) {
        wakeLockDao.insert(
            WakeLock_st(itemSt.name, itemSt.flag, itemSt.allowTimeinterval)
        )
    }
}