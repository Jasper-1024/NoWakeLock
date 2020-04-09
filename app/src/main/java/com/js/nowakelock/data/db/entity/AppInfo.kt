package com.js.nowakelock.data.db.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.js.nowakelock.base.BaseItem

@Entity(tableName = "appInfo")
data class AppInfo(
    @PrimaryKey
    var packageName: String = "",
    var uid: Int = 0,
    var label: String = "",
    var icon: Int = 0,
    var system: Boolean = false,
    var enabled: Boolean = false,
    var persistent: Boolean = false,
    var processName: String = "",
    var flag: Boolean = true,
    var count: Int = 0,
    var blockCount: Int = 0,
    var countTime: Long = 0,
    var blockCountTime: Long = 0
//    @Ignore
//    var wakeLocks: HashMap<String, WakeLock> = HashMap()
) : BaseItem {
    @Ignore
    override fun getID() = packageName

    @Ignore
    override fun getContent(): Int = count
}