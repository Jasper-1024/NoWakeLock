package com.js.nowakelock.data.repository.backup

import com.js.nowakelock.data.db.dao.AppDaDao
import com.js.nowakelock.data.db.dao.DADao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BackupRepo(private var appDaDao: AppDaDao, private var daDao: DADao) {
    suspend fun getBackup(): Backup = withContext(Dispatchers.IO) {
        val appSts = async { appDaDao.loadAllAppSts() }
        val sts = async { daDao.loadAllSts() }
        return@withContext Backup(appSts.await(), sts.await())
    }

    suspend fun restoreBackup(backup: Backup) = withContext(Dispatchers.IO) {
        appDaDao.insert(backup.appSts)
        daDao.insert(backup.sts)
    }
}