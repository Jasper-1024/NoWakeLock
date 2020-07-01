package com.js.nowakelock.data.repository.appinforepository

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.BackupDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfoSt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class IAppInfoRepository(private var appInfoDao: AppInfoDao, private var backupDao: BackupDao) :
    AppInfoRepository {
    private val pm: PackageManager = BasicApp.context.packageManager

    /**get all database app info*/
    override fun getAppLists() = appInfoDao.loadAppInfos()

    /**Sync installed apps*/
    override suspend fun syncAppInfos() {
        val dbAppInfos = getDBPackageNames()
        val sysAppInfos = getPackageNames()

        insertAll(sysAppInfos.keys subtract dbAppInfos.keys, sysAppInfos)
        deleteAll(dbAppInfos.keys subtract sysAppInfos.keys, dbAppInfos)

        updateCount()

        updateFlag()
    }

    /**get AppSetting*/
    override fun getAppSetting(packageName: String): LiveData<AppInfoSt> =
        appInfoDao.loadAppSetting(packageName)

    /**save AppSetting*/
    override suspend fun saveAppSetting(appinfoSt: AppInfoSt) = withContext(Dispatchers.IO) {
        appInfoDao.insert(appinfoSt)
    }

    /**insert all app info*/
    private suspend fun insertAll(
        packageNames: Set<String>,
        sysAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            sysAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.insert(it.values as MutableCollection<AppInfo>)
            }
        }
    }

    /**delete all app info*/
    private suspend fun deleteAll(
        packageNames: Set<String>,
        dbAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            dbAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.deleteAll(it.values as MutableCollection<AppInfo>)
            }
        }
    }

    /**get all installed app*/
    @SuppressLint("WrongConstant")
    private suspend fun getPackageNames(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.Default) {
            val sysAppInfo = ArrayMap<String, AppInfo>()

            pm.getInstalledApplications(0).forEach {
                sysAppInfo[it.packageName] = getAppInfo(it)
            }

            return@withContext sysAppInfo
        }

    /**get all database app*/
    private suspend fun getDBPackageNames(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val dbAppInfos = ArrayMap<String, AppInfo>()
            appInfoDao.loadAllAppInfos().forEach {
                dbAppInfos[it.packageName] = it
            }
            return@withContext dbAppInfos
        }

    /**get AppInfo from ApplicationInfo*/
    private fun getAppInfo(ai: ApplicationInfo): AppInfo {
        val esetting = pm.getApplicationEnabledSetting(ai.packageName)
        val enabled = ai.enabled &&
                (esetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ||
                        esetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
        val persistent =
            ai.flags and ApplicationInfo.FLAG_PERSISTENT != 0 || "android" == ai.packageName
        val system = ai.flags and
                (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

        return AppInfo(
            ai.packageName,
            ai.uid,
            pm.getApplicationLabel(ai) as String,
            ai.icon,
            system,
            enabled,
            persistent,
            ai.processName
        )
    }

    private suspend fun updateCount() = withContext(Dispatchers.IO) {
        appInfoDao.loadPackageNames().forEach {
            appInfoDao.updateAppInfoCount(it)
        }
    }

    private suspend fun updateFlag() = withContext(Dispatchers.IO) {
        val tmp = appInfoDao.loadAllAppInfos()
        tmp.forEach {
            it.flag = getModFlag(it.packageName)
        }
        appInfoDao.insert(tmp)
    }

    private suspend fun getModFlag(packageName: String): Boolean = withContext(Dispatchers.IO) {

        val appInfoSt = async { backupDao.loadAppSt(packageName) }
        val lAlarmSt = async { backupDao.loadAlarmSts(packageName).validData() }
        val lServiceSt = async { backupDao.loadServiceSts(packageName).validData() }
        val lWakeLockSt = async { backupDao.loadWakeLockSts(packageName).validData() }

        return@withContext modFlag(
            appInfoSt.await(),
            lAlarmSt.await(),
            lServiceSt.await(),
            lWakeLockSt.await()
        )
    }

    private suspend fun modFlag(
        appInfoSt: AppInfoSt?,
        l_Alarm: List<ItemSt>,
        l_Service: List<ItemSt>,
        l_Wakelock: List<ItemSt>
    ): Boolean = withContext(Dispatchers.Default) {
        return@withContext !(appInfoSt == null && l_Alarm.isEmpty() && l_Service.isEmpty() && l_Wakelock.isEmpty())
    }

    private fun <T : ItemSt> List<T>.validData(): List<T> {
        return this.filter {
            !it.flag || (it.allowTimeinterval != 0.toLong())
        }
    }
}