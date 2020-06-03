package com.js.nowakelock.data.repository.inforepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

interface InfoRepository {
    fun getItem(name: String): Flow<Item>
    fun getAppInfo(packageName: String): Flow<AppInfo>

    suspend fun getItem_st(name: String): ItemSt
    suspend fun setItem_st(itemSt: ItemSt)
}