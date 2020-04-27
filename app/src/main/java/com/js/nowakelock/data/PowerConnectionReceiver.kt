package com.js.nowakelock.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.dao.WakeLockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
//            LogUtil.d("PowerConnectionReceiver", "ACTION_POWER_DISCONNECTED")
            GlobalScope.launch {
                clearWakelockdb(
                    AppDatabase.getInstance(BasicApp.context).wakeLockDao()
                )
            }
        } else if (intent.action.equals(Intent.ACTION_POWER_CONNECTED)) {
//            LogUtil.d("PowerConnectionReceiver", "ACTION_POWER_CONNECTED")
            GlobalScope.launch {
                clearWakelockdb(
                    AppDatabase.getInstance(BasicApp.context).wakeLockDao()
                )
            }
        }
    }

    private suspend fun clearWakelockdb(wakeLockDao: WakeLockDao) = withContext(Dispatchers.IO) {
        val wakelocks = wakeLockDao.loadAllWakeLocks()
        if (wakelocks.isNotEmpty()) {
            wakelocks.forEach {
                it.count = 0
                it.countTime = 0
                it.blockCount = 0
                it.blockCountTime = 0
            }
            wakeLockDao.insert(wakelocks)
        }
    }
}
