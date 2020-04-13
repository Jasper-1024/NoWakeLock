package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLock
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

//    override suspend fun getFlag(pN: String, wN: String): Boolean {
//        return wakeLockDao.loadFlag(wN)
//    }

//    override suspend fun upCount(pN: String, wN: String) {
//        val tmp = withContext(Dispatchers.IO) {
//            wakeLockDao.loadwakelockName(wN)
//        }
//        if (tmp == "") {
//            withContext(Dispatchers.IO) {
//                wakeLockDao.insert(WakeLock(pN, wN, count = 1))
//            }
//        } else {
//            withContext(Dispatchers.IO) {
//                wakeLockDao.upCount(wN)
//                wakeLockDao.upCountP(pN)
//            }
//        }
//    }
//
//    override suspend fun upBlockCount(pN: String, wN: String) {
//        val tmp = withContext(Dispatchers.IO) {
//            wakeLockDao.loadwakelockName(wN)
//        }
//        if (tmp == "") {
//            withContext(Dispatchers.IO) {
//                wakeLockDao.insert(WakeLock(pN, wN, count = 1, blockCount = 1))
//            }
//        } else {
//            withContext(Dispatchers.IO) {
//                wakeLockDao.upCount(wN)
//                wakeLockDao.upCountP(pN)
//                wakeLockDao.upBlockCount(wN)
//                wakeLockDao.upBlockCountP(pN)
//            }
//        }
//    }

//    override suspend fun rstCount(pN: String, wN: String) =
//        withContext(Dispatchers.IO) { wakeLockDao.rstCount(wN) }

//    override suspend fun setWakeLockFlag(wakeLock: WakeLock) = withContext(Dispatchers.IO) {
//        wakeLockDao.insert(wakeLock)
//    }

}