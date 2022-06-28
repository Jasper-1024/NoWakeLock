package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.Info

@Dao
interface InfoDao : BaseDao<Info> {

    @Query("select * from info")
    suspend fun loadInfos(): List<Info>

    @Query("select * from info where type = :type")
    suspend fun loadInfos(type: Type): List<Info>

    @Query("select * from info where packageName = :packageName")
    suspend fun loadInfos(packageName: String): List<Info>

    @Query("select * from info where packageName = :packageName and type = :type")
    suspend fun loadInfos(packageName: String, type: Type): List<Info>

    @Query("select * from info where name = :name")
    suspend fun loadInfo(name: String): Info?

    @Query("select * from info where name = :name and type = :type")
    suspend fun loadInfo(name: String, type: Type): Info?

    @Query("update info set count = count+:count where name = :name and type = :type")
    suspend fun upCount(count: Int, name: String, type: Type)

    suspend fun upCountPO(name: String, type: Type) = upCount(1, name, type)

    @Query("update info set blockCount = blockCount+:count where name = :name and type = :type")
    suspend fun upBlockCount(count: Int, name: String, type: Type)

    suspend fun upBlockCountPO(name: String, type: Type) = upBlockCount(1, name, type)

    @Query("update info set countTime = countTime+:time where name = :name and type = :type")
    suspend fun upCountTime(time: Long, name: String, type: Type)


    @Query("update info set count = 0 where type = :type")
    suspend fun rstAllCount(type: Type)

    @Query("update info set countTime = 0 where type = :type")
    suspend fun rstAllCountTime(type: Type)
}