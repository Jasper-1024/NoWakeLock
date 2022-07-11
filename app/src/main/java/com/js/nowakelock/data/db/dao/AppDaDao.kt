package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.js.nowakelock.data.db.entity.AppDA
import com.js.nowakelock.data.db.entity.AppSt
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDaDao : BaseDao<AppSt> {
    @Transaction
    @Query("select * from appInfo where packageName = :packageName")
    fun loadAppDa(packageName: String): Flow<AppDA>

    @Query("select * from appSt where packageName_st = :packageName")
    fun loadAppSt(packageName: String): Flow<AppSt?>
}