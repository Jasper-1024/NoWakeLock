package com.js.nowakelock.data.provider

import android.content.Context
import android.os.Bundle
import androidx.preference.PreferenceManager
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.xposedhook.model.DB
import com.js.nowakelock.xposedhook.model.DBModel
import com.js.nowakelock.xposedhook.model.STModel
import com.js.nowakelock.xposedhook.model.XPM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.reflect.KSuspendFunction1

class ProviderHandler(
    context: Context
) {
    private val TAG = "ProviderHandler"

    private var db: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        @Volatile
        private var instance: ProviderHandler? = null

        fun getInstance(context: Context): ProviderHandler {
            if (instance == null) {
                instance = ProviderHandler(context)
            }
            return instance!!
        }
    }

    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return when (methodName) {
            XPM.dbMethod -> db(bundle)
            XPM.stMethod -> st(bundle)
            else -> null
        }
    }

    private fun getType(bundle: Bundle): String {
        return bundle.getString(XPM.type) ?: ""
    }

    private fun db(bundle: Bundle): Bundle? {
//        LogUtil.d("Xposed.NoWakeLock","db")
        val tmp = getDBModel(bundle)
        tmp?.let {
            GlobalScope.launch {
                try {
                    dbMethod(getType(bundle))(tmp.dbHM.values)
                } catch (e: Exception) {
                    LogUtil.d(TAG, e.toString())
                }
            }
        }
        return null
    }

    private fun getDBModel(bundle: Bundle): DBModel? {
        val tmp = bundle.getSerializable(XPM.db)
//        LogUtil.d("Xposed.NoWakeLock","db tmp1 $tmp")
        return tmp as DBModel?
    }

    private suspend fun dbMethod(type: String): KSuspendFunction1<MutableCollection<DB>, Unit> {
        return when (type) {
            XPM.alarm -> ::dbAlarm
            XPM.service -> ::dbService
            XPM.wakelock -> ::dbWakelock
            else -> ::update
        }
    }

    private suspend fun dbAlarm(list: MutableCollection<DB>) {
        list.forEach {
            val tmp: Alarm = db.alarmDao().loadAlarm(it.name)
                ?: Alarm(it.name, it.packageName)
            tmp.count += it.count
            tmp.blockCount += it.blockCount
            db.alarmDao().insert(tmp)
        }
    }

    private suspend fun dbWakelock(list: MutableCollection<DB>) {
        list.forEach {
            val tmp: WakeLock = db.wakeLockDao().loadWakeLock(it.name)
                ?: WakeLock(it.name, it.packageName)
            tmp.countTime += it.countTime
            tmp.blockCountTime += it.blockCountTime
            db.wakeLockDao().insert(tmp)
        }
    }

    private suspend fun dbService(list: MutableCollection<DB>) {
        //TODO()
    }

    private suspend fun update(list: MutableCollection<DB>) {
    }

    private fun st(bundle: Bundle): Bundle? {
//        LogUtil.d("Xposed.NoWakeLock", "st")
        return stBundle(stMethod(getType(bundle)).invoke())
    }

    private fun stBundle(stModel: STModel): Bundle {
//        LogUtil.d("Xposed.NoWakeLock", "stBundle $stModel")
        return Bundle().apply {
            putSerializable(XPM.st, stModel)
        }
    }

    private fun stMethod(type: String): () -> STModel {
        return when (type) {
            XPM.alarm -> ::stAlarm
            XPM.service -> ::stService
            XPM.wakelock -> ::stWakelock
            else -> ::st
        }
    }

    private fun stAlarm(): STModel {
        val tmp = STModel()

        runBlocking(Dispatchers.IO) {
            db.alarmDao().loadAlarm_st().forEach {
                tmp.flagHM[it.alarmName] = it.flag
                tmp.atIHM[it.alarmName] = it.allowTimeinterval
            }
            db.appInfoDao().loadAppSettings().forEach {
                tmp.rEHM[it.packageName] = it.rE_Alarm
            }
            getSetting(tmp)
        }
        return tmp
    }

    private fun stService(): STModel {
        TODO()
    }

    private fun stWakelock(): STModel {
        val tmp = STModel()

        runBlocking(Dispatchers.IO) {
            db.wakeLockDao().loadWakeLock_st().forEach {
                tmp.flagHM[it.wakeLockName] = it.flag
                tmp.atIHM[it.wakeLockName] = it.allowTimeinterval
            }
            db.appInfoDao().loadAppSettings().forEach {
                tmp.rEHM[it.packageName] = it.rE_Wakelock
            }
            getSetting(tmp)
        }
        return tmp
    }

    private fun getSetting(stModel: STModel) {
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(BasicApp.context)
        stModel.recordFlag = sharedPreferences.getBoolean("recordFlag", true)
        stModel.updateDb = (sharedPreferences.getInt("updateDb", 120) * 1000).toLong()
        stModel.updateST = (sharedPreferences.getInt("updateST", 60) * 1000).toLong()
    }

    private fun st(): STModel {
        return STModel()
    }
}