package com.js.nowakelock.data.repository

import androidx.lifecycle.Observer
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WakeLockRepository(private var wakeLockDao: WakeLockDao) {
    /*cach data*/
    private var wakeLocksHM = HashMap<String, WakeLock>()

    /*cach data*/
    private var packageNamesHS = HashSet<String>()

    fun getWakeLocks(packageName: String) = wakeLockDao.loadAllWakeLocks(packageName)

    /**upCount*/
    suspend fun upCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        if (isInstalledApp(pN)) {
            val wakeLock = wakeLocksHM[wN] ?: ciWakeLock(pN, wN)
            wakeLock.count++
        }
    }

    /**upBlockCount*/
    suspend fun upBlockCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val wakeLock = wakeLocksHM[wN] ?: ciWakeLock(pN, wN)
        wakeLock.count++
        wakeLock.blockCount++
    }

    /***/
    suspend fun rstCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val wakeLock = wakeLocksHM[wN] ?: ciWakeLock(pN, wN)
    }

    /**set wakeLocksHM -> database */
    suspend fun insertAll() =
        withContext(Dispatchers.IO) { wakeLockDao.insertAll(wakeLocksHM.values) }

    /**is app install at system?*/
    fun isInstalledApp(packageName: String): Boolean = packageNamesHS.contains(packageName)

    /**set packageNamesHS`s observe
     * before use WakeLockRepository, must setup first.
     * */
    private fun setObserve() {
        val packageNames = wakeLockDao.loadPackageNames()
        val observer = Observer<List<String>> { packageNames ->
            packageNamesHS.clear()
            packageNamesHS.addAll(packageNames)
        }
        packageNames.observeForever(observer)
    }

    /**load WakeLocksHM
     * before use WakeLockRepository, must setup first.
     * */
    private fun loadWakeLocksHM() {
        val wakeLocks = wakeLockDao.loadAllWakeLocks()
        wakeLocks.forEach {
            wakeLocksHM[it.wakeLockName] = it
        }
    }

    /**creat and insert now wakelock*/
    private fun ciWakeLock(pN: String, wN: String): WakeLock {
        val wakeLock = WakeLock(pN, wN)
        wakeLocksHM[wakeLock.wakeLockName] = wakeLock
        return wakeLock
    }

//    companion object {
//        @Volatile
//        private var instance: WakeLockRepository? = null
//        fun getInstance(wakeLockDao: WakeLockDao) =
//            instance ?: synchronized(this) {
//                instance ?: WakeLockRepository(wakeLockDao).also {
//                    it.loadWakeLocksHM()
//                    it.setObserve()
//                    instance = it
//                }
//            }
//    }

}