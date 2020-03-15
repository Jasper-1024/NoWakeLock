package com.js.nowakelock

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import androidx.annotation.VisibleForTesting
import com.js.nowakelock.db.AppDatabase
import com.js.nowakelock.db.entity.AppInfo
import com.js.nowakelock.db.entity.WakeLock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataRepository(private var database: AppDatabase) {

    private val pm: PackageManager = BasicApp.context.packageManager
    private var appInfosHM = HashMap<String, AppInfo>()

    /**app start, get all installed app*/
    suspend fun getAppInfoHMs() = withContext(Dispatchers.IO) {
        val packageNames = HashSet<String>()
        packageNames.addAll(database.appInfoDao().loadPackageNames())
        /*in case new application install*/
        for (ai in pm.getInstalledApplications(0)) {
            if (packageNames.contains(ai.processName)) {
                appInfosHM[ai.processName] = database.appInfoDao().loadAppInfo(ai.processName)
            } else {
                appInfosHM[ai.processName] = getAppInfo(ai)
            }
        }
    }

    /**app start ,get all wakelocks*/
    suspend fun getWakeLockHMs() = withContext(Dispatchers.IO) {
        for (pN in appInfosHM.keys) {
            val wakeLocks = database.wakeLockDao().loadAllWakeLocks(pN)
            appInfosHM[pN]?.wakeLocks?.putAll(wakeLocks.associateBy { it.wakeLockName })
        }
    }

    /**ui get list of installed app*/
    suspend fun getAppInfos(): List<AppInfo> = withContext(Dispatchers.Default) {
        return@withContext appInfosHM.values.toList()
    }

    /**reFresh AppInfo from reload install application*/
    suspend fun reFreshAppInfos(): List<AppInfo> = withContext(Dispatchers.Default) {
        reFreshAppInfoHMs()
        return@withContext getAppInfos()
    }


    /**reFresh AppInfo from reload install application*/
    suspend fun reFreshAppInfoHMs() = withContext(Dispatchers.IO) {
        /*get installed app list again*/
        for (ai in pm.getInstalledApplications(0)) {
            /*if new app install*/
            if (!appInfosHM.containsKey(ai.processName)) {
                inserAppInfo(ai)
            }
        }
    }

    /**save all to db*/
    suspend fun saveAllToDb() = withContext(Dispatchers.IO) {
        database.appInfoDao().insertAll(appInfosHM.values)
        appInfosHM.values.forEach {
            database.wakeLockDao().insertAll(it.wakeLocks.values)
        }
    }

    /**upCount*/
    suspend fun upCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val appInfo = appInfosHM[pN] ?: inserAppInfo(pN)
        appInfo.count++
        val wakeLock = appInfo.wakeLocks[wN] ?: insertWakeLock(pN, wN)
        wakeLock.count++
    }

    /**upBlockCount*/
    suspend fun upBlockCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val appInfo = appInfosHM[pN] ?: inserAppInfo(pN)
        appInfo.count++
        appInfo.blockCount++
        val wakeLock = appInfo.wakeLocks[wN] ?: insertWakeLock(pN, wN)
        wakeLock.count++
        wakeLock.blockCount++
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getCount(pN: String, wN: String = ""): Int? {
        return if (wN == "") {
            appInfosHM[pN]?.count
        } else {
            appInfosHM[pN]?.wakeLocks!![wN]?.count
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getBlockCount(pN: String, wN: String = ""): Int? {
        return if (wN == "") {
            appInfosHM[pN]?.blockCount
        } else {
            appInfosHM[pN]?.wakeLocks!![wN]?.blockCount
        }
    }


    /**rstCount*/
    suspend fun rstCount(pN: String, wN: String) = withContext(Dispatchers.Default) {
        val appInfo = appInfosHM[pN] ?: inserAppInfo(pN)
        appInfo.blockCount = 0
        appInfo.count = 0
        val wakeLock = appInfo.wakeLocks[wN] ?: insertWakeLock(pN, wN)
        wakeLock.blockCount = 0
        wakeLock.count = 0
    }

    /**insert new app*/
    private fun inserAppInfo(ai: ApplicationInfo): AppInfo {
        val appInfo = getAppInfo(ai)
        appInfosHM[ai.processName] = appInfo
        return appInfo
    }

    private fun inserAppInfo(pN: String): AppInfo = inserAppInfo(pm.getApplicationInfo(pN, 0))

    /**insert now wakelock*/
    private fun insertWakeLock(pN: String, wN: String): WakeLock {
        val wakeLock = WakeLock(pN, wN)
        appInfosHM[pN]?.wakeLocks!![wN] = wakeLock
        return wakeLock
    }


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

//    suspend fun getPackageNames(): List<String> {
//        var tmp = ArrayList<String>()
//        for (ai in pm.getInstalledApplications(0)) {
//            tmp.add(ai.processName)
//        }
//        return tmp
//    }
}

