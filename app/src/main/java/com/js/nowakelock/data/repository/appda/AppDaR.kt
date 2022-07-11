package com.js.nowakelock.data.repository.appda

import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.dao.AppDaDao
import com.js.nowakelock.data.db.entity.AppCount
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppSt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class AppDaR(private val appDaDao: AppDaDao) : AppDaRepo {
    override fun getAppDa(packageName: String): Flow<AppDA> =
        appDaDao.loadAppDa(packageName).distinctUntilChanged().map { appDa ->
            LogUtil.d("test", "1")
            if (appDa.count == null) {
                appDa.count = AppCount(packageName = appDa.info.packageName)
            }
            if (appDa.st == null) {
                appDa.st = AppSt(packageName = appDa.info.packageName)
            }
            appDa
        }

    override suspend fun setAppSt(appSt: AppSt) {
        appDaDao.insert(appSt)
    }
}