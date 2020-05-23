package com.js.nowakelock.data.repository

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.AppInfo_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class mAppInfoRepository(private var appInfoDao: AppInfoDao) : AppInfoRepository {
    private val pm: PackageManager = BasicApp.context.packageManager

    /**get all database app info*/
    override fun getAppLists() = appInfoDao.loadAppInfos()

    /**Sync installed apps*/
    override suspend fun syncAppInfos() {
        val dbAppInfos = getDBPackageNames()
        val sysAppInfos = getPackageNames()

        insertAll(sysAppInfos.keys subtract dbAppInfos.keys, sysAppInfos)
        deleteAll(dbAppInfos.keys subtract sysAppInfos.keys, dbAppInfos)
    }

    /**get AppSetting*/
    override fun getAppSetting(packageName: String): LiveData<AppInfo_st> =
        appInfoDao.loadAppSetting(packageName)

    /**save AppSetting*/
    override suspend fun saveAppSetting(appinfoSt: AppInfo_st) = withContext(Dispatchers.IO) {
        appInfoDao.insert(appinfoSt)
    }

    /**insert all app info*/
    private suspend fun insertAll(
        packageNames: Set<String>,
        sysAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            sysAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.insertAll(it.values as MutableCollection<AppInfo>)
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

//    private suspend fun updateCount(packageNames: List<String>) = withContext(Dispatchers.IO) {
//        packageNames.forEach {
//            appInfoDao.updateAppInfoCount(it)
//            appInfoDao.updateAppInfoBlockCount(it)
//        }
//    }

}