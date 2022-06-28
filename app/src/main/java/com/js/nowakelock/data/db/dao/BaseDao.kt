package com.js.nowakelock.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

sealed interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objS: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(objS: MutableCollection<T>)

    @Delete
    suspend fun delete(obj: T)

    @Delete
    suspend fun delete(objS: List<T>)

    @Delete
    suspend fun delete(objS: MutableCollection<T>)
}