package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.ServiceInfo
import com.js.nowakelock.data.db.entity.ServiceSt
import kotlinx.coroutines.flow.Flow

@Dao
interface ServiceDao {
    /**for service fragment,no packageName*/
    @Transaction
    @Query("select * from service")
    fun loadServices(): Flow<List<Service>>

    /**for service fragment*/
    @Transaction
    @Query("select * from service where service_packageName = :packageName")
    fun loadServices(packageName: String): Flow<List<Service>>

    /**for ProviderHandler,save service to db*/
    @Query("select * from service where serviceName = :serviceName")
    suspend fun loadService(serviceName: String): ServiceInfo?

    /**for BroadcastReceiver clear db*/
    @Query("select * from service")
    suspend fun loadAllServices(): List<ServiceInfo>

    /**insert*/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceInfo: ServiceInfo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(serviceInfos: List<ServiceInfo>)

    /**delete*/
    @Delete
    suspend fun delete(serviceInfo: ServiceInfo)

    @Delete
    suspend fun delete(serviceInfos: List<ServiceInfo>)

    /**service_st*/
    @Query("select serviceName_st from service_st where serviceName_st = :serviceName")
    fun loadServiceStName(serviceName: String): String?

    /**service_st*/
    @Query("select * from service_st where serviceName_st = :serviceName")
    fun loadServiceSt(serviceName: String): ServiceSt?

    /**for ContentProvider*/
    @Query("select * from service_st")
    fun loadServiceSt(): List<ServiceSt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(serviceSt: ServiceSt)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertST(serviceSt: List<ServiceSt>)
}