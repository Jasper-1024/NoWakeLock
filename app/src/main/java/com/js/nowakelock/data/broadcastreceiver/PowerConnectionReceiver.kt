package com.js.nowakelock.data.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.getCPResult
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.dao.DADao
import com.js.nowakelock.data.db.dao.InfoDao
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.provider.ProviderMethod
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(BasicApp.context /* Activity context */)
        val powerFlag = sharedPreferences.getBoolean("powerFlag", true)
        val clearFlag = sharedPreferences.getBoolean("clearFlag", true)

        if (intent.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {//POWER DISCONNECTED
            CoroutineScope(Dispatchers.IO).launch {
                clear(powerFlag, clearFlag)
            }
        } else if (intent.action.equals(Intent.ACTION_POWER_CONNECTED)) { //POWER CONNECTED
            CoroutineScope(Dispatchers.IO).launch {
                clear(powerFlag, clearFlag)
            }
        }
    }

    private suspend fun clear(powerFlag: Boolean, clearFlag: Boolean) =
        withContext(Dispatchers.IO) {
            if (powerFlag) {
                clearCPAll()
                val db = AppDatabase.getInstance(BasicApp.context)
                clearCount(db.infoDao())

                if (clearFlag) {
                    clearAll(db.infoDao(), db.dADao())
                }

            }
        }

    private suspend fun clearCount(infoDao: InfoDao) {
        infoDao.rstAllCount()
        infoDao.rstAllCountTime()
    }

    private suspend fun clearAll(infoDao: InfoDao, daDao: DADao) {
        infoDao.clearAll()
        // for st info
        infoDao.insert(
            daDao.loadStsDB().map {
                Info(name = it.name, type = it.type, packageName = it.packageName)
            })
    }

    private fun clearCPAll() {
        val args = Bundle()
        getCPResult(BasicApp.context, ProviderMethod.ClearAll.value, args)
    }
}
