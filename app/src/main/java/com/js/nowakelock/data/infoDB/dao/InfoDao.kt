package com.js.nowakelock.data.infoDB.dao

import androidx.room.*
import com.js.nowakelock.data.infoDB.entity.Info

@Dao
interface InfoDao {

    @Query("select * from info")
    suspend fun loadInfos(): List<Info>

    @Query("select * from info where type = :type")
    suspend fun loadInfos(type: String): List<Info>

    @Query("select * from info where packageName = :packageName")
    suspend fun loadInfo(packageName: String): Info?

    @Query("select * from info where packageName = :packageName and type = :type")
    suspend fun loadInfo(packageName: String, type: String): Info?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(info: Info)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(infos: List<Info>)

    @Delete
    suspend fun delete(infos: MutableCollection<Info>)

    @Delete
    suspend fun delete(info: Info)

    @Query("update info set count = count+:count where name = :name and type = :type")
    suspend fun upCount(count: Int, name: String, type: String)

    suspend fun upCountPO(name: String, type: String) = upCount(1, name, type)

    @Query("update info set blockCount = blockCount+:count where name = :name and type = :type")
    suspend fun upBlockCount(count: Int, name: String, type: String)

    suspend fun upBlockCountPO(name: String, type: String) = upBlockCount(1, name, type)

    @Query("update info set countTime = countTime+:time where name = :name and type = :type")
    suspend fun upCountTime(time: Long, name: String, type: String)

    @Query("update info set blockCountTime = blockCountTime+:time where name = :name and type = :type")
    suspend fun upBlockCountTime(time: Long, name: String, type: String)

    @Query("update info set count = 0 where type = :type")
    suspend fun rstAllCount(type: String)

    @Query("update info set blockCountTime = 0 where type = :type")
    suspend fun rstAllBlockCount(type: String)

    @Query("update info set countTime = 0 where type = :type")
    suspend fun rstAllCountTime(type: String)

    @Query("update info set blockCountTime = 0 where type = :type")
    suspend fun rstAllBlockCountTime(type: String)

}