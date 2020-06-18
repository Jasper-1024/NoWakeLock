package com.js.nowakelock.data.repository.backuprepository

import com.js.nowakelock.data.db.base.ItemSt
import com.js.nowakelock.data.db.entity.AppInfoSt

data class AppB(
    var packageName: String,
    var version: String = "",
    var appInfoSt: AppInfoSt = AppInfoSt(),
    var l_Alarm: List<ItemSt> = mutableListOf(),
    var l_Service: List<ItemSt> = mutableListOf(),
    var l_Wakelock: List<ItemSt> = mutableListOf()
)