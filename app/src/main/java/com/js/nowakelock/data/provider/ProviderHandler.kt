package com.js.nowakelock.data.provider

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.js.nowakelock.base.ALUtil
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.WLUtil
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.collections.set

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
            "saveAL" -> saveAL(bundle)
            "getALStHM" -> getALStHM(bundle)
            "saveWL" -> saveWL(bundle)
            "wlStsHM" -> getWLSt(bundle)
            "test" -> test(bundle)
            else -> null
        }
    }

    //update db alarm
    @Synchronized
    private fun saveAL(bundle: Bundle): Bundle? {
        val tmp1 = ALUtil.getAlarm(bundle)

        tmp1.let {
            GlobalScope.launch {
                try {
                    val tmp: Alarm = db.alarmDao().loadAlarm(it.alarmName)
                        ?: Alarm(it.alarmName, it.packageName)
                    tmp.count += it.count
                    tmp.blockCount += it.blockCount
                    db.alarmDao().insert(tmp)
//                    LogUtil.d("Xposed.NoWakeLock", "saveAL ${tmp}")
                } catch (e: Exception) {
                    LogUtil.d(TAG, e.toString())
                }
            }
        }
        return null
    }

    @Synchronized
    private fun getALStHM(bundle: Bundle): Bundle? {
        val alFlagHM: HashMap<String, Boolean> = HashMap<String, Boolean>()
        val alReHM: HashMap<String, Set<String>> = HashMap<String, Set<String>>()

        runBlocking(Dispatchers.Default) {
            db.alarmDao().loadAlarm_st().forEach {
                alFlagHM[it.alarmName] = it.flag
            }
            db.appInfoDao().loadAppSettings().forEach {
                alReHM[it.packageName] = it.rE_Alarm
            }
        }

        val tmp = Bundle()
        tmp.putSerializable("alFlagHM", alFlagHM)
        tmp.putSerializable("alReHM", alReHM)
        LogUtil.d("Xposed.NoWakeLock", "Bundle1 ${tmp}")
        return tmp
    }

    //update db wakelock
    @Synchronized
    private fun saveWL(bundle: Bundle): Bundle? {
        val tmi = WLUtil.getWakeLock(bundle)

        tmi.let {
            GlobalScope.launch {
                try {
                    val tmp: WakeLock = db.wakeLockDao().loadWakeLock(it.wakeLockName)
                        ?: WakeLock(it.wakeLockName, it.packageName, it.uid)
                    tmp.count += it.count
                    tmp.countTime += it.countTime
                    tmp.blockCount += it.blockCount
                    tmp.blockCountTime += it.blockCountTime
                    db.wakeLockDao().insert(tmp)
                } catch (e: Exception) {
                    LogUtil.d(TAG, e.toString())
                }
            }
        }
        return null
    }

    @Synchronized
    private fun getWLSt(bundle: Bundle): Bundle? {
        val wlFlagHM: HashMap<String, Boolean> = HashMap<String, Boolean>()
        val wlATIHM: HashMap<String, Long> = HashMap<String, Long>()
        val wlREHM: HashMap<String, Set<String>> = HashMap<String, Set<String>>()

        runBlocking(Dispatchers.Default) {
            db.wakeLockDao().loadWakeLock_st().forEach {
                wlFlagHM[it.wakeLockName] = it.flag
                wlATIHM[it.wakeLockName] = it.allowTimeinterval
            }
            db.appInfoDao().loadAppSettings().forEach {
                wlREHM[it.packageName] = it.rE_Wakelock
            }
        }

        val tmp = Bundle()
        tmp.putSerializable("wlFlagHM", wlFlagHM)
        tmp.putSerializable("wlwlATIHM", wlATIHM)
        tmp.putSerializable("wlREHM", wlREHM)
//        LogUtil.d("Xposed.NoWakeLock", "Bundle1 ${tmp} ${tmp.size()}")
        return tmp
    }

    @VisibleForTesting
    fun test(bundle: Bundle): Bundle? {
        val test = bundle.get("Test") as String?

        LogUtil.d(TAG, "$test")

        val tmp2 = HashMap<String, WakeLock_st>()
        tmp2["test"] = WakeLock_st("test", false, 1000)

        val tmp = Bundle()
        tmp.putString("Test", "Test")
        tmp.putSerializable("test", tmp2)
        return tmp
    }
}