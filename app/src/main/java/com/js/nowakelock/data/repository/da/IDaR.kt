package com.js.nowakelock.data.repository.da

import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.St
import kotlinx.coroutines.flow.Flow

class IDaR(private val daDao: DADao) : DaRepo {

    override fun getDa(name: String, type: Type, userId: Int): Flow<DA> {
        return daDao.loadDA(name, type, userId)
    }

    override suspend fun insertSt(st: St) {
        daDao.insert(st)
    }

}