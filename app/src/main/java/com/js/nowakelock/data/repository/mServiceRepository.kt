package com.js.nowakelock.data.repository

import androidx.lifecycle.LiveData
import com.js.nowakelock.data.db.dao.ServiceDao
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.Service_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class mServiceRepository(private val serviceDao: ServiceDao) : ServiceRepository {

    override fun getServices(): LiveData<List<Service>> {
        return serviceDao.loadServices()
    }

    override fun getServices(packageName: String): LiveData<List<Service>> {
        return serviceDao.loadServices(packageName)
    }

    override suspend fun sync(pN: String) {
        TODO("NOE YET")
    }

    override suspend fun getService_st(sN: String): Service_st = withContext(Dispatchers.IO) {
        return@withContext serviceDao.loadService_st(sN) ?: Service_st(sN)
    }

    override suspend fun setService_st(serviceSt: Service_st) = withContext(Dispatchers.IO) {
        serviceDao.insert(serviceSt)
    }
}