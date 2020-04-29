package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class mWakeLockRepository(private var wakeLockDao: WakeLockDao) : WakeLockRepository {

    override fun getWakeLocks(): LiveData<List<WakeLock>> = wakeLockDao.loadWakeLocks()

    override fun getWakeLocks(packageName: String) = wakeLockDao.loadWakeLocks(packageName)

    override suspend fun syncWakelocks(pN: String) = withContext(Dispatchers.IO) {
        //not empty then do something
        if (wakeLockDao.countWakeLocks(pN) != 0) {
            wakeLockDao.updateAppInfoCount(pN)
            wakeLockDao.updateAppInfoBlockCount(pN)
        }
    }

    override suspend fun getWakeLock_st(wN: String): WakeLock_st = withContext(Dispatchers.IO) {
        return@withContext wakeLockDao.loadWakeLock_st(wN) ?: WakeLock_st(wN)
    }

    override suspend fun setWakeLock_st(wN_st: WakeLock_st) = withContext(Dispatchers.IO) {
        wakeLockDao.insert(wN_st)
    }

}