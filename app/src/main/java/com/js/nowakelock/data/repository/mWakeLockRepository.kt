package com.js.nowakelock.data.repository

import com.js.nowakelock.data.db.dao.WakeLockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class mWakeLockRepository(private var wakeLockDao: WakeLockDao) : WakeLockRepository {

    override fun getWakeLocks(packageName: String) = wakeLockDao.loadAllWakeLocks(packageName)

    override suspend fun getFlag(pN: String, wN: String): Boolean =
        withContext(Dispatchers.IO) { wakeLockDao.loadFlag(wN) }

    override suspend fun upCount(pN: String, wN: String) =
        withContext(Dispatchers.IO) { wakeLockDao.upCount(wN) }

    override suspend fun upBlockCount(pN: String, wN: String) =
        withContext(Dispatchers.IO) { wakeLockDao.upBlockCount(wN) }

    override suspend fun rstCount(pN: String, wN: String) =
        withContext(Dispatchers.IO) { wakeLockDao.rstCount(wN) }

    override suspend fun syncWakelocks(pN: String) = withContext(Dispatchers.IO) {
        //not empty then do something
        if (wakeLockDao.countWakeLocks(pN) != 0) {
            wakeLockDao.updateAppInfoCount(pN)
            wakeLockDao.upBlockCount(pN)
        }
    }

}