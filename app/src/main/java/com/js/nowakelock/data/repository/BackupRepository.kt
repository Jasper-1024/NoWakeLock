package com.js.nowakelock.data.repository

import com.js.nowakelock.data.backup.AppB
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.BackupDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackupRepository(private var backupDao: BackupDao) {
    suspend fun getAppB(packageName: String): AppB? = withContext(Dispatchers.IO) {

        val tmp = backupDao.loadAppSt(packageName)
        if (tmp == null) {
            return@withContext AppB(packageName)
        } else {
            return@withContext AppB(packageName).apply {
                appInfoSt = tmp
            }
        }

        return@withContext AppB(packageName)
    }

    private suspend fun getLItemB(list: List<ItemSt>): List<ItemSt> =
        withContext(Dispatchers.Default) {
            return@withContext list.validData()
        }

    private fun <T : ItemSt> List<T>.validData(): List<T> {
        return this.filter {
            !it.flag || (it.allowTimeinterval != 0.toLong())
        }
    }

//    private fun <T : ItemSt> List<T>.toItemB(): List<ItemB> {
//        return this.map {
//            ItemB(it.name, it.flag, it.allowTimeinterval)
//        }
//    }

}