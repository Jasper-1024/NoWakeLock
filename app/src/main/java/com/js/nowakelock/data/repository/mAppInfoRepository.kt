package com.js.nowakelock.data.repository

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.MATCH_ALL
import androidx.collection.ArrayMap
import com.js.nowakelock.BasicApp
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
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
        insertAll(getDiff(sysAppInfos.keys.toList(), dbAppInfos), sysAppInfos)
        deleteAll(getDiff(dbAppInfos, sysAppInfos.keys.toList()))
    }

//    /**TO//DO: save wakeLocksHM to db
//     * calculate appinfos`s Count/BlockCount
//     *  */
//    suspend fun updateCounts() = withContext(Dispatchers.IO) {
//        suspend fun calculateCount(wakeLocks: List<WakeLock>): Int =
//            withContext(Dispatchers.Default) {
//                var count = 0
//                wakeLocks.forEach {
//                    count += it.count
//                }
//                return@withContext count
//            }
//
//        suspend fun calculateBlockCount(wakeLocks: List<WakeLock>): Int =
//            withContext(Dispatchers.Default) {
//                var blockCount = 0
//                wakeLocks.forEach {
//                    blockCount += it.blockCount
//                }
//                return@withContext blockCount
//            }
//
////        getDBPackageNames().forEach {
////            val wakeLocks = database.wakeLockDao().loadAllWakeLockByPn(it)
////            appInfoDao.upCount(it, calculateCount(wakeLocks))
////            appInfoDao.upBlockCount(it, calculateBlockCount(wakeLocks))
////        }
//    }
//

    /**take difference*/
    private suspend fun getDiff(a: List<String>, b: List<String>): Set<String> =
        withContext(Dispatchers.Default) {
            a subtract b
        }

    private suspend fun deleteAll(packageNames: Set<String>) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            val appInfos = ArrayList<AppInfo>()
            for (packageName in packageNames) {
                appInfos.add(appInfoDao.loadAppInfo(packageName))
            }
            appInfoDao.deleteAll(appInfos)
        }
    }

    @SuppressLint("RestrictedApi")
    private suspend fun insertAll(
        packageNames: Set<String>,
        applicationInfos: ArrayMap<String, ApplicationInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            if (packageNames.isNotEmpty()) {
                val appInfos = ArrayList<AppInfo>()
                for (packageName in packageNames) {
                    try {
                        appInfos.add(getAppInfo(applicationInfos[packageName]!!))
                    } catch (err: Exception) {
                        LogUtil.d(TAG + "insertAll", packageName + " " + err)
                    }
                }
                appInfoDao.insertAll(appInfos)
                appInfoDao.insert(
                    AppInfo(
                        packageName = "android",
                        appName = BasicApp.context.getString(R.string.android)
                    )
                )
            }
        }
    }

    /**get all installed app*/
    @SuppressLint("WrongConstant")
    private suspend fun getPackageNames(): ArrayMap<String, ApplicationInfo> =
        withContext(Dispatchers.Default) {
            val applicationInfos = ArrayMap<String, ApplicationInfo>()
            for (ai in pm.getInstalledApplications(0)) {
                applicationInfos[ai.processName] = ai
            }
            return@withContext applicationInfos
        }

    /**get all database app*/
    private suspend fun getDBPackageNames(): List<String> =
        withContext(Dispatchers.IO) { appInfoDao.loadPackageNames() }

    @SuppressLint("WrongConstant")
    private fun getAppInfo(packageName: String): AppInfo =
        getAppInfo(pm.getApplicationInfo(packageName, MATCH_ALL))

    private fun getAppInfo(ai: ApplicationInfo): AppInfo {
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
            ai.processName,
            ai.uid,
            ai.loadLabel(pm).toString(),
            ai.icon,
            "",
            system,
            enabled,
            persistent
        )
    }
}