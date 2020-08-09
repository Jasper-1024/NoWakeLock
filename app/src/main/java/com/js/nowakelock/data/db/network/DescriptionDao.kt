package com.js.nowakelock.data.db.network

import androidx.room.*

@Dao
interface DescriptionDao {

    @Query("select * from description")
    suspend fun loadDescriptions(): List<Description>

    @Query("select * from description where name = :name")
    suspend fun loadDescription(name: String): List<Description>

    @Query("select * from description where name = :name and language = :language")
    suspend fun loadDescription(name: String, language: String): Description?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(description: Description)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(descriptions: List<Description>)

    @Delete
    suspend fun delete(description: Description)

    @Delete
    suspend fun delete(descriptions: List<Description>)
}