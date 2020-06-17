package com.js.nowakelock.data.repository

import com.js.nowakelock.data.backup.AppB
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.BackupDao
import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BackupRepository(private var backupDao: BackupDao) {

    suspend fun getAppBs(): List<AppB>? = withContext(Dispatchers.IO) {
        return@withContext backupDao.loadAppNames()
            .mapNotNull { getAppB(it) }
            .filter { !isAppBEmpty(it) }
    }

    suspend fun setAppBs(list: List<AppB>) = withContext(Dispatchers.Default) {
        list.forEach {
            setAppB(it)
        }
    }

    private suspend fun getAppB(packageName: String): AppB? = withContext(Dispatchers.IO) {
        val tmp = backupDao.loadAppSt(packageName)
        tmp?.let {
            return@withContext AppB(packageName).apply {
                appInfoSt = tmp
                l_Alarm = backupDao.loadAlarmSts(packageName).validData()
                l_Service = backupDao.loadServiceSts(packageName).validData()
                l_Wakelock = backupDao.loadWakeLockSts(packageName).validData()
            }
        }
        return@withContext null
    }

    private suspend fun setAppB(appB: AppB) = withContext(Dispatchers.IO) {
        backupDao.insert(appB.appInfoSt)
        insertList<AlarmSt>(appB.l_Alarm)
        insertList<ServiceSt>(appB.l_Service)
        insertList<WakeLockSt>(appB.l_Wakelock)
    }

    private fun isAppBEmpty(appB: AppB): Boolean {
        return isAppInfoStEmpty(appB.appInfoSt) && appB.l_Alarm.isEmpty() && appB.l_Service.isEmpty() && appB.l_Wakelock.isEmpty()
    }

    private fun isAppInfoStEmpty(appInfoSt: AppInfoSt): Boolean {
        return appInfoSt.flag && appInfoSt.allowTimeinterval == 0.toLong()
    }

    private fun <T : ItemSt> List<T>.validData(): List<T> {
        return this.filter {
            !it.flag || (it.allowTimeinterval != 0.toLong())
        }
    }

    private suspend inline fun <reified T : ItemSt> insertList(list: List<ItemSt>) =
        withContext(Dispatchers.Default) {
            list.map {
                val t: T = T::class.java.newInstance()
                t.apply {
                    name = it.name
                    packageName = it.packageName
                    flag = it.flag
                    allowTimeinterval = it.allowTimeinterval
                }
                t
            }.forEach {
                insert(it)
            }
        }

    private suspend inline fun <reified T : ItemSt> insert(t: T) = withContext(Dispatchers.IO) {
        when (T::class.java) {
            AlarmSt::class.java -> backupDao.insert(t as AlarmSt)
            ServiceSt::class.java -> backupDao.insert(t as ServiceSt)
            WakeLockSt::class.java -> backupDao.insert(t as WakeLockSt)
        }
    }

}