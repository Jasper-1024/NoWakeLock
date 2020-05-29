package com.js.nowakelock.data.repository

import com.js.nowakelock.data.base.Item
import com.js.nowakelock.data.base.Item_st
import kotlinx.coroutines.flow.Flow

interface FRepository {
    fun getLists(): Flow<List<Item>>
    fun getLists(packageName: String): Flow<List<Item>>

    suspend fun sync(pN: String)

    suspend fun getItem_st(name: String): Item_st
    suspend fun setItem_st(itemSt: Item_st)
}