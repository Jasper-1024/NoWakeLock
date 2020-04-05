package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.WakeLock

interface WakeLockRepository {
    fun getWakeLocks(packageName: String): LiveData<List<WakeLock>>

//    suspend fun getFlag(pN: String, wN: String): Boolean
//
//    suspend fun upCount(pN: String, wN: String)
//
//    suspend fun upBlockCount(pN: String, wN: String)

    suspend fun rstCount(pN: String, wN: String)

    suspend fun syncWakelocks(pN: String)

    suspend fun setWakeLockFlag(wakeLock: WakeLock)
}