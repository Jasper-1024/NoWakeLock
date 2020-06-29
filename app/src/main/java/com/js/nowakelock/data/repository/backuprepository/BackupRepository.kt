package com.js.nowakelock.data.repository.backuprepository

import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.BackupDao
import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BackupRepository(private var backupDao: BackupDao) {

    suspend fun getAppBs(): List<AppB>? = withContext(Dispatchers.IO) {
        return@withContext backupDao.loadAppNames()
            .mapNotNull {
                getAppB(it)
            }
    }

    suspend fun setAppBs(list: List<AppB>) = withContext(Dispatchers.Default) {
        list.forEach {
            setAppB(it)
        }
    }

    private suspend fun getAppB(packageName: String): AppB? = withContext(Dispatchers.IO) {

        fun appB(
            appInfoSt: AppInfoSt?,
            l_Alarm: List<ItemSt>,
            l_Service: List<ItemSt>,
            l_Wakelock: List<ItemSt>
        ): AppB? {
            return if (appInfoSt == null && l_Alarm.isEmpty() && l_Service.isEmpty() && l_Wakelock.isEmpty()) {
                null
            } else {
                AppB(
                    packageName = packageName,
                    l_Alarm = l_Alarm,
                    l_Service = l_Service,
                    l_Wakelock = l_Wakelock
                )
            }
        }

        val appInfoSt = async { backupDao.loadAppSt(packageName) }
        val lAlarmSt = async { backupDao.loadAlarmSts(packageName).validData() }
        val lServiceSt = async { backupDao.loadServiceSts(packageName).validData() }
        val lWakeLockSt = async { backupDao.loadWakeLockSts(packageName).validData() }

        return@withContext appB(
            appInfoSt.await(),
            lAlarmSt.await(),
            lServiceSt.await(),
            lWakeLockSt.await()
        )
    }

    private suspend fun setAppB(appB: AppB) = withContext(Dispatchers.IO) {
        backupDao.insert(appB.appInfoSt)
        insertList<AlarmSt>(appB.l_Alarm)
        insertList<ServiceSt>(appB.l_Service)
        insertList<WakeLockSt>(appB.l_Wakelock)
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