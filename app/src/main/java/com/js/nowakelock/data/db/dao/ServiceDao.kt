package com.js.nowakelock.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.Service_st

@Dao
interface ServiceDao {
    /**for service fragment,no packageName*/
    @Query("select * from service")
    fun loadServices(): LiveData<List<Service>>

    /**for service fragment*/
    @Query("select * from service where service_packageName = :packageName")
    fun loadServices(packageName: String): LiveData<List<Service>>

    /**for ProviderHandler,save service to db*/
    @Query("select * from service where serviceName = :serviceName")
    suspend fun loadService(serviceName: String): Service?

    /**for BroadcastReceiver clear db*/
    @Query("select * from service")
    suspend fun loadAllServices(): List<Service>

    /**insert*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(service: Service)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(services: List<Service>)

    /**delete*/
    @Delete
    suspend fun delete(service: Service)

    @Delete
    suspend fun delete(services: List<Service>)

    /**service_st*/
    @Query("select * from service_st where serviceName_st = :serviceName")
    fun loadService_st(serviceName: String): Service_st?

    /**for ContentProvider*/
    @Query("select * from service_st")
    fun loadService_st(): List<Service_st>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceSt: Service_st)
}