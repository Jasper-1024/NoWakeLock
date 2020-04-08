package com.js.nowakelock.data.repository

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.collection.ArrayMap
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.entity.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for applist_viewmodel
 * */
class mAppInfoRepository(private var appInfoDao: AppInfoDao) : AppInfoRepository {
    private val pm: PackageManager = BasicApp.context.packageManager
    private val TAG = "test_AR"

    /**get all dadabase appinfos*/
    override fun getAppLists() = appInfoDao.loadAllAppInfos()

    /**Sync installed apps*/
    override suspend fun syncAppInfos() {
        val dbAppInfos = getDBPackageNames()
        val sysAppInfos = getPackageNames()

        insertAll(sysAppInfos.keys subtract dbAppInfos.keys, sysAppInfos)
        deleteAll(dbAppInfos.keys subtract sysAppInfos.keys, dbAppInfos)

        updateCount(appInfoDao.loadWLPackageNames())
    }

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
                //just in case some app use the same processName
                if (sysAppInfo[it.processName] == null) {
                    sysAppInfo[it.processName] = getAppInfo(it)
//                    LogUtil.d("test1","${it.processName}  ${pm.getApplicationLabel(it)}")
                } else {
                    sysAppInfo[pm.getApplicationLabel(it) as String] =
                        getAppInfo(it, pm.getApplicationLabel(it) as String)
//                    LogUtil.d("test2","${pm.getApplicationLabel(it)}")
                }
            }

            return@withContext sysAppInfo
        }

    /**get all database app*/
    private suspend fun getDBPackageNames(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val dbAppInfos = ArrayMap<String, AppInfo>()
            appInfoDao.loadPackageNames().forEach {
                dbAppInfos[it] = appInfoDao.loadAppInfo(it)
            }
            return@withContext dbAppInfos
        }

//    @SuppressLint("WrongConstant")
//    private fun getAppInfo(packageName: String): AppInfo =
//        getAppInfo(pm.getApplicationInfo(packageName, MATCH_ALL))

    private fun getAppInfo(ai: ApplicationInfo): AppInfo =
        getAppInfo(ai, ai.processName)

    private fun getAppInfo(ai: ApplicationInfo, pN: String): AppInfo {
        /*属性*/
        val esetting = pm.getApplicationEnabledSetting(ai.packageName)
        val enabled = ai.enabled &&
                (esetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ||
                        esetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
        val persistent =
            ai.flags and ApplicationInfo.FLAG_PERSISTENT != 0 || "android" == ai.packageName
        val system = ai.flags and
                (ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0

        return AppInfo(
            pN,
            ai.uid,
            pm.getApplicationLabel(ai) as String,
            ai.icon,
            system,
            enabled,
            persistent
        )
    }

    private suspend fun updateCount(packageNames: List<String>) = withContext(Dispatchers.IO) {
        packageNames.forEach {
            appInfoDao.updateAppInfoCount(it)
            appInfoDao.updateAppInfoBlockCount(it)
        }
    }

}