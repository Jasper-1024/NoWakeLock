package com.js.nowakelock.data.repository.da

import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.St
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class DaR(private val daDao: DADao) : DaRepo {
    /**
     * get a signal of da
     * @param name String
     * @param type Type
     * @return Flow<DA>
     */
    override fun getDa(name: String, type: Type): Flow<DA> =
        daDao.loadDA(name, type).distinctUntilChanged().map {
            if (it.info.count != 0)// calculate blockCountTime
                it.info.blockCountTime =
                    it.info.blockCount * (it.info.countTime / it.info.count)
            if (it.st == null) {
                it.st = St(
                    name = it.info.name,
                    type = it.info.type,
                    packageName = it.info.packageName
                )
            }
            it
        }

    /**
     * install st
     * @param st St
     */
    override suspend fun insertSt(st: St) {
        daDao.insert(st)
    }
}