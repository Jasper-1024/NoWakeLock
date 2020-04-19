package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st

interface WakeLockRepository {

    fun getWakeLocks(): LiveData<List<WakeLock>>

    fun getWakeLocks(packageName: String): LiveData<List<WakeLock>>

    suspend fun syncWakelocks(pN: String)

    suspend fun getWakeLock_st(wN: String): WakeLock_st

    suspend fun setWakeLock_st(wN_st: WakeLock_st)
//    suspend fun getFlag(pN: String, wN: String): Boolean
//
//    suspend fun upCount(pN: String, wN: String)
//
//    suspend fun upBlockCount(pN: String, wN: String)

//    suspend fun rstCount(pN: String, wN: String)
}