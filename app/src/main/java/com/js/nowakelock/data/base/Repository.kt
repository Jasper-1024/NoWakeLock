package com.js.nowakelock.data.base

import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getLists(): Flow<List<Item>>
    fun getLists(packageName: String): Flow<List<Item>>

    suspend fun sync(pN: String)

    suspend fun getItem_st(name: String): Item_st
    suspend fun setItem_st(itemSt: Item_st)
}