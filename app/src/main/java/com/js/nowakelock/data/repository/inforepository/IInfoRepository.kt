package com.js.nowakelock.data.repository.inforepository

import com.js.nowakelock.data.db.base.Item
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt
import kotlinx.coroutines.flow.Flow

class IInfoRepository(private val infoDao: InfoDao, private val type: String) : InfoRepository {

    private var info: Info

    init {
        info = when (type) {
            "alarm" -> {
                Info(infoDao::loadAlarm, infoDao::loadAlarmSt)
            }
            "service" -> {
                Info(infoDao::loadService, infoDao::loadServiceSt)
            }
            "wakelock" -> {
                Info(infoDao::loadWakeLock, infoDao::loadWakeLockSt)
            }
            else -> {
                Info(infoDao::loadWakeLock, infoDao::loadWakeLockSt)
            }
        }
    }

    override fun getItem(name: String): Flow<Item> {
        return info.item(name)
    }

    override fun getAppInfo(packageName: String): Flow<AppInfo> {
        return infoDao.loadAppInfo(packageName)
    }

    override suspend fun getItem_st(name: String): ItemSt {
        return info.getSt(name) ?: getInstance(name).apply { saveInstance(this) }
    }

    override suspend fun setItem_st(itemSt: ItemSt) {
        saveInstance(itemSt)
    }

    data class Info(
        var item: (name: String) -> Flow<Item>,
        var getSt: (name: String) -> ItemSt?
//        var setSt: (itemSt: ItemSt) -> Unit
    )

    private fun getInstance(name: String): ItemSt {
        return when (type) {
            "alarm" -> {
                AlarmSt(name)
            }
            "service" -> {
                ServiceSt(name)
            }
            "wakelock" -> {
                WakeLockSt(name)
            }
            else -> {
                WakeLockSt(name)
            }
        }
    }

    private suspend fun saveInstance(itemSt: ItemSt) {
        when (type) {
            "alarm" -> infoDao.insert(itemSt as AlarmSt)
            "service" -> infoDao.insert(itemSt as ServiceSt)
            "wakelock" -> infoDao.insert(itemSt as WakeLockSt)
            else -> infoDao.insert(itemSt as WakeLockSt)
        }
    }
}