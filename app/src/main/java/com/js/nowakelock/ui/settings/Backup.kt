package com.js.nowakelock.ui.settings

import com.js.nowakelock.data.db.entity.AlarmSt
import com.js.nowakelock.data.db.entity.AppInfoSt
import com.js.nowakelock.data.db.entity.ServiceSt
import com.js.nowakelock.data.db.entity.WakeLockSt

class Backup {
    lateinit var lalarmSt: List<AlarmSt>
    lateinit var lserviceSt: List<ServiceSt>
    lateinit var lwakelockSt: List<WakeLockSt>
    lateinit var lappInfoSt: List<AppInfoSt>
}