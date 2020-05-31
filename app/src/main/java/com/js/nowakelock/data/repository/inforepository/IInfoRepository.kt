package com.js.nowakelock.data.repository.inforepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.flow.Flow

class IInfoRepository(private val infoDao: InfoDao) : InfoRepository {
    override fun getItem(name: String): Flow<Item> {
        TODO("Not yet implemented")
    }

    override fun getAppInfo(packageName: String): AppInfo {
        TODO("Not yet implemented")
    }

    override suspend fun getItem_st(name: String): ItemSt {
        TODO("Not yet implemented")
    }

    override suspend fun setItem_st(itemSt: ItemSt) {
        TODO("Not yet implemented")
    }
}