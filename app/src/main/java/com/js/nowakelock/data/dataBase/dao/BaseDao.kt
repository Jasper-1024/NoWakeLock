package com.js.nowakelock.data.dataBase.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.js.nowakelock.data.dataBase.entity.Info

sealed interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objS: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objS: MutableCollection<T>)

    @Delete
    suspend fun delete(obj: Info)

    @Delete
    suspend fun delete(objS: List<T>)

    @Delete
    suspend fun delete(objS: MutableCollection<T>)
}