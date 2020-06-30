package com.js.nowakelock.data.repository.frepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLockSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class IWakelockR(private var wakeLockDao: WakeLockDao) :
    FRepository {

    override fun getLists(): Flow<List<Item>> {
        return wakeLockDao.loadWakeLocks().map { items ->
            items.forEach {
                if (it.st == null) {
                    it.st = WakeLockSt(
                        name = it.info.name,
                        packageName = it.info.packageName
                    ).apply { wakeLockDao.insertST(this) }
                }
                it.stFlag.set(it.st!!.flag)
            }
            items
        }
    }

    override fun getLists(packageName: String): Flow<List<Item>> {
        return wakeLockDao.loadWakeLocks(packageName)
    }

    override suspend fun sync(pN: String) {
    }

    override suspend fun getItemSt(name: String): ItemSt = withContext(Dispatchers.IO) {
        return@withContext wakeLockDao.loadWakeLockSt(name)
            ?: WakeLockSt(name).apply { wakeLockDao.insertST(this) }
    }

    override suspend fun setItemSt(itemSt: ItemSt) = withContext(Dispatchers.IO) {
        wakeLockDao.insertST(
            WakeLockSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        )
    }

    override suspend fun setItemSt(itemSts: List<ItemSt>) = withContext(Dispatchers.IO) {
        val tmp = itemSts.map { itemSt ->
            WakeLockSt(itemSt.name, itemSt.flag, itemSt.allowTimeinterval, itemSt.packageName)
        }
        wakeLockDao.insertST(tmp)
    }
}