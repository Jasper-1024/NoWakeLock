package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.Service_st

interface ServiceRepository {

    fun getServices(): LiveData<List<Service>>
    fun getServices(packageName: String): LiveData<List<Service>>

    suspend fun sync(pN: String)

    suspend fun getService_st(sN: String): Service_st
    suspend fun setService_st(serviceSt: Service_st)
}