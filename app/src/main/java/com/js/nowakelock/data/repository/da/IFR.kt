package com.js.nowakelock.data.repository.da

import android.os.Bundle
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.getCPResult
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.db.entity.St
import com.js.nowakelock.data.provider.ProviderMethod
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

open class IFR(private val daDao: DADao) : FR {

    open val type: Type = Type.UnKnow

    private val tag = "NoWakelock"

    override fun getDAs(): Flow<List<DA>> {
        return daDao.loadDAs(type).distinctUntilChanged().map { das ->

            das.forEach {
                if (it.st == null) {
                    it.st = St(
                        name = it.info.name,
                        type = it.info.type,
                        packageName = it.info.packageName
                    )
                }
            }
            das
        }
    }

    override fun getDAs(packageName: String): Flow<List<DA>> {
        return daDao.loadAppDAs(packageName, type).distinctUntilChanged().map { das ->
            das.forEach {
                if (it.st == null) {
                    it.st = St(
                        name = it.info.name,
                        type = it.info.type,
                        packageName = it.info.packageName
                    )
                }
            }
            das
        }
    }

    override suspend fun insertInfos(infos: List<Info>) {
        daDao.insert(infos)
    }

    override suspend fun insertSt(st: St) {
        daDao.insert(st)
    }

    override suspend fun getSts(): Flow<List<St>> {
        return daDao.loadSts(type).distinctUntilChanged()
    }

    override suspend fun getCPInfos(packageName: String): List<Info> {
        val args = Bundle().let {
            it.putString("type", type.value)
            it.putString("packageName", packageName)
            it
        }

        val result = getCPResult(BasicApp.context, ProviderMethod.LoadInfos.value, args)

        return if (result != null) {
            val infos = result.getSerializable("infos") as Array<Info>?
            infos?.toList() ?: emptyList()
        } else {
            emptyList()
        }
    }

    override suspend fun resumeSt2Info() {
        daDao.insert(
            daDao.loadStsDB(type).map {
                Info(name = it.name, type = it.type, packageName = it.packageName)
            })
    }

}