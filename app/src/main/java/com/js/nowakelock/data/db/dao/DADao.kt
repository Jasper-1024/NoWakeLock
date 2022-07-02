package com.js.nowakelock.data.db.dao

import androidx.room.*
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.db.entity.St
import kotlinx.coroutines.flow.Flow

@Dao
interface DADao : BaseDao<St> {

    @Transaction
    @Query("SELECT * FROM info where type_info = :type ")
    fun loadDAs(type: Type): Flow<List<DA>>

    @Transaction
    @Query("select * from info where type_info = :type and packageName_info = :packageName")
    fun loadAppDAs(packageName: String, type: Type): Flow<List<DA>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(infos: List<Info>)

//    @Query("select * from st")
//    fun loadSts(): Flow<List<St>>
//
    @Query("select * from st where type_st = :type")
    fun loadSts(type: Type): Flow<List<St>>
//
//
//    @Query("select * from st where type_st = :type and name_st = :name")
//    suspend fun loadSt(name: String, type: Type): St?
}