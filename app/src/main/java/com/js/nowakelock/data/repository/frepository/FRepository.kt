package com.js.nowakelock.data.repository.frepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import kotlinx.coroutines.flow.Flow

interface FRepository {
    fun getLists(): Flow<List<Item>>
    fun getLists(packageName: String): Flow<List<Item>>

    suspend fun sync(pN: String)

    suspend fun getItemSt(name: String): ItemSt
    suspend fun setItemSt(itemSt: ItemSt)
    suspend fun setItemSt(itemSts: List<ItemSt>)
}