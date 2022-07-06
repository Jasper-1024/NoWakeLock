package com.js.nowakelock.data.repository.da

import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.db.entity.St
import kotlinx.coroutines.flow.Flow

interface FR {
    fun getDAs(): Flow<List<DA>>
    fun getDAs(packageName: String): Flow<List<DA>>

    suspend fun insertInfos(infos: List<Info>)
    suspend fun insertSt(st: St)

    suspend fun getSts(): Flow<List<St>>

    /**
     * call ContentProvider for infos
     * @param packageName String
     * @return List<Info>
     */
    suspend fun getCPInfos(packageName: String): List<Info>

    /**
     * db st -> db info
     */
    suspend fun resumeSt2Info()
}