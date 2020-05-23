package com.js.nowakelock.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.preference.PreferenceManager
import com.js.nowakelock.BasicApp
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.dao.AlarmDao
import com.js.nowakelock.data.db.dao.ServiceDao
import com.js.nowakelock.data.db.dao.WakeLockDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(BasicApp.context /* Activity context */)
        val powerFlag = sharedPreferences.getBoolean("powerFlag", true)

        //POWER DISCONNECTED
        if (intent.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
//            LogUtil.d("PowerConnectionReceiver", "ACTION_POWER_DISCONNECTED")
            if (powerFlag) {
                GlobalScope.launch {
                    clearWakelockdb(
                        AppDatabase.getInstance(BasicApp.context).wakeLockDao()
                    )
                    clearAlarmdb(AppDatabase.getInstance(BasicApp.context).alarmDao())
                    clearServicedb(AppDatabase.getInstance(BasicApp.context).serviceDao())
                }
            }
            //POWER CONNECTED
        } else if (intent.action.equals(Intent.ACTION_POWER_CONNECTED)) {
//            LogUtil.d("PowerConnectionReceiver", "ACTION_POWER_CONNECTED")
            if (powerFlag) {
                GlobalScope.launch {
                    clearWakelockdb(
                        AppDatabase.getInstance(BasicApp.context).wakeLockDao()
                    )
                    clearAlarmdb(AppDatabase.getInstance(BasicApp.context).alarmDao())
                    clearServicedb(AppDatabase.getInstance(BasicApp.context).serviceDao())
                }
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

    private suspend fun clearAlarmdb(alarmDao: AlarmDao) = withContext(Dispatchers.IO) {
        val alarms = alarmDao.loadAllAlarms()
        if (alarms.isNotEmpty()) {
            alarms.forEach {
                it.count = 0
                it.countTime = 0
                it.blockCount = 0
                it.blockCountTime = 0
            }
            alarmDao.insert(alarms)
        }
    }

    private suspend fun clearServicedb(serviceDao: ServiceDao) = withContext(Dispatchers.IO) {
        val services = serviceDao.loadAllServices()
        if (services.isNotEmpty()) {
            services.forEach {
                it.count = 0
                it.countTime = 0
                it.blockCount = 0
                it.blockCountTime = 0
            }
            serviceDao.insert(services)
        }
    }
}
