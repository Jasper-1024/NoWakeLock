package com.js.nowakelock.data.repository.appdas

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.collection.ArrayMap
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.getCPResult
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.dao.AppInfoDao
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.entity.AppCount
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.provider.ProviderMethod
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class AppDasAR(private val appInfoDao: AppInfoDao, private val daDao: DADao) : AppDasRepo {

    private val pm: PackageManager = BasicApp.context.packageManager

    override fun getAppDAs(): Flow<List<AppDA>> =
        appInfoDao.loadAppInfos().distinctUntilChanged().map { appDas ->
            appDas.forEach {
                if (it.count == null) {
                    it.count = AppCount(packageName = it.info.packageName, userId = it.info.userId)
                }
            }
            appDas
        }


    override suspend fun getAppInfo(packageName: String): AppInfo =
        appInfoDao.loadAppInfo(packageName)

    override suspend fun syncAppInfos() = withContext(Dispatchers.Default) {
        val dbAppInfos = getDBAppInfos()//db AppInfos
        val sysAppInfos = getSysAppInfos()//system AppInfos

        //取差集更新删除
        insertAll(sysAppInfos.keys subtract dbAppInfos.keys, sysAppInfos)
        deleteAll(dbAppInfos.keys subtract sysAppInfos.keys, dbAppInfos)
    }

    override suspend fun syncInfos(): Unit = withContext(Dispatchers.IO) {
        val args = Bundle().let {
            it.putString("type", Type.UnKnow.value)
            it.putString("packageName", "")
            it
        }

        val result = getCPResult(BasicApp.context, ProviderMethod.LoadInfos.value, args)

        result?.let {
            val infos = result.getSerializable("infos") as Array<Info>?
            infos?.toList()?.let {
                daDao.insert(it)
            }
        }
    }

    /**db 插入新应用*/
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

    /**db 删除卸载应用*/
    private suspend fun deleteAll(
        packageNames: Set<String>,
        dbAppInfos: ArrayMap<String, AppInfo>
    ) = withContext(Dispatchers.IO) {
        if (packageNames.isNotEmpty()) {
            dbAppInfos.filter { it.key in packageNames }.let {
                appInfoDao.delete(it.values as MutableCollection<AppInfo>)
            }
        }
    }

    // 获取全部 system AppInfos
    @SuppressLint("QueryPermissionsNeeded")
    private suspend fun getSysAppInfos(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val sysAppInfo = ArrayMap<String, AppInfo>()

//            LogUtil.d("test2", "${pm.getInstalledApplications(0).size}")

            pm.getInstalledApplications(0).forEach {
                sysAppInfo[it.packageName] = getSysAppInfo(it)
            }

            return@withContext sysAppInfo
        }

    // 获取全部数据库 AppInfos
    private suspend fun getDBAppInfos(): ArrayMap<String, AppInfo> =
        withContext(Dispatchers.IO) {
            val dbAppInfos = ArrayMap<String, AppInfo>()
            appInfoDao.loadAppInfosDB().forEach {
                dbAppInfos[it.packageName] = it
            }
            return@withContext dbAppInfos
        }

    //获取单个 AppInfo
    private fun getSysAppInfo(ai: ApplicationInfo): AppInfo {
        val easting = pm.getApplicationEnabledSetting(ai.packageName)
        val enabled = ai.enabled &&
                (easting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT ||
                        easting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
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
}