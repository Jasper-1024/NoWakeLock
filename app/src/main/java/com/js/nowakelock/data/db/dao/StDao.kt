package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.St
import kotlinx.coroutines.flow.Flow

@Dao
interface StDao : BaseDao<St> {
    @Query("select * from st")
    fun loadSts(): Flow<List<St>>

    @Query("select * from st where type = :type")
    fun loadSts(type: Type): Flow<List<St>>

    @Query("select * from st where type = :type and packageName = :packageName")
    fun loadAppSts(packageName: String, type: Type): Flow<List<St>>

    @Query("select * from st where type = :type and name = :name")
    suspend fun loadSt(name: String, type: Type): St?
}