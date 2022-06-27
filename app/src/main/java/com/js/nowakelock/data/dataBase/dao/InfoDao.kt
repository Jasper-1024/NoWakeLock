package com.js.nowakelock.data.dataBase.dao

import androidx.room.*
import com.js.nowakelock.data.dataBase.Type
import com.js.nowakelock.data.dataBase.entity.Info

@Dao
interface InfoDao : BaseDao<Info> {

    @Query("select * from info")
    suspend fun loadInfos(): List<Info>

    @Query("select * from info where type = :type")
    suspend fun loadInfos(type: Type): List<Info>

    @Query("select * from info where packageName = :packageName")
    suspend fun loadInfo(packageName: String): Info?

    @Query("select * from info where packageName = :packageName and type = :type")
    suspend fun loadInfo(packageName: String, type: Type): Info?

    @Query("update info set count = count+:count where name = :name and type = :type")
    suspend fun upCount(count: Int, name: String, type: Type)

    suspend fun upCountPO(name: String, type: Type) = upCount(1, name, type)

    @Query("update info set blockCount = blockCount+:count where name = :name and type = :type")
    suspend fun upBlockCount(count: Int, name: String, type: Type)

    suspend fun upBlockCountPO(name: String, type: Type) = upBlockCount(1, name, type)

    @Query("update info set countTime = countTime+:time where name = :name and type = :type")
    suspend fun upCountTime(time: Long, name: String, type: Type)

    @Query("update info set blockCountTime = blockCountTime+:time where name = :name and type = :type")
    suspend fun upBlockCountTime(time: Long, name: String, type: Type)

    @Query("update info set count = 0 where type = :type")
    suspend fun rstAllCount(type: Type)

    @Query("update info set blockCountTime = 0 where type = :type")
    suspend fun rstAllBlockCount(type: Type)

    @Query("update info set countTime = 0 where type = :type")
    suspend fun rstAllCountTime(type: Type)

    @Query("update info set blockCountTime = 0 where type = :type")
    suspend fun rstAllBlockCountTime(type: Type)
}