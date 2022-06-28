package com.js.nowakelock.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.js.nowakelock.data.db.entity.AppSt

@Dao
interface AppStDao : BaseDao<AppSt> {
    @Query("select * from appSt where packageName_st = :packageName")
    fun loadAppSt(packageName: String): AppSt
}