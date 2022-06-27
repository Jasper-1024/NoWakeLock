package com.js.nowakelock.data.dataBase.dao

import androidx.room.Dao
import androidx.room.Query
import com.js.nowakelock.data.dataBase.entity.AppSt

@Dao
interface AppStDao : BaseDao<AppSt> {
    @Query("select * from appSt where packageName_st = :packageName")
    fun loadAppSt(packageName: String): AppSt
}