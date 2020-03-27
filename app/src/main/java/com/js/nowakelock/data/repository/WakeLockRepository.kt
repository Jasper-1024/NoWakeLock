package com.js.nowakelock.data.repository

import androidx.lifecycle.Observer
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.dao.WakeLockDao
import com.js.nowakelock.data.db.entity.WakeLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WakeLockRepository(private var wakeLockDao: WakeLockDao) {

    init {
        GlobalScope.launch {
            setObserve()//packageNamesHS
            loadWakeLocksHM()//WakeLocksHM
        }
    }

//    private val TAG = "WakeLockRepository"

    /*cach data*/
    var wakeLocksHM = HashMap<String, WakeLock>()

    /*cach data*/
    var packageNamesHS = HashSet<String>()

    fun getWakeLocks(packageName: String) = wakeLockDao.loadAllWakeLocks(packageName)

    /**get flag*/
    suspend fun getFlag(pN: String, wN: String): Boolean = withContext(Dispatchers.Default) {
        if (isInstalledApp(pN)) {
            val wakeLock = wakeLocksHM[wN] ?: ciWakeLock(pN, wN)
            return@withContext wakeLock.flag
        }
        //default true
        return@withContext true
    }


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

    /**rst Count / BlockCount*/
    suspend fun rstCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val wakeLock = wakeLocksHM[wN] ?: ciWakeLock(pN, wN)
        wakeLock.count = 0
        wakeLock.blockCount = 0
    }

    /**set wakeLocksHM -> database */
    suspend fun insertAll() =
        withContext(Dispatchers.IO) { wakeLockDao.insertAll(wakeLocksHM.values) }

    /**is app install at system?*/
    fun isInstalledApp(packageName: String): Boolean = packageNamesHS.contains(packageName)

    suspend fun init() {
        setObserve()//packageNamesHS
        loadWakeLocksHM()//WakeLocksHM
    }

    /**set packageNamesHS`s observe
     * before use WakeLockRepository, must setup first.
     * */
    suspend fun setObserve() = withContext(Dispatchers.IO) {
        val packageNames = wakeLockDao.loadPackageNames()
        val observer = Observer<List<String>> { pNs ->
            packageNamesHS.clear()
            packageNamesHS.addAll(pNs)
        }
        withContext(Dispatchers.Main) {
        packageNames.observeForever(observer)
        }
//        LogUtil.d("test1", "setObserve")
    }

    /**load WakeLocksHM
     * before use WakeLockRepository, must setup first.
     * */
    suspend fun loadWakeLocksHM() = withContext(Dispatchers.IO) {
        val wakeLocks = wakeLockDao.loadAllWakeLocks()
        //only set once
        if (!wakeLocks.isEmpty() and wakeLocksHM.isEmpty()) {
            wakeLocksHM.clear()
            wakeLocks.forEach {
                wakeLocksHM[it.wakeLockName] = it
            }
        }
//        LogUtil.d("test1", "loadWakeLocksHM")
    }

    /**creat and insert new wakelock*/
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