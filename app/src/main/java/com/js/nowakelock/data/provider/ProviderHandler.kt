package com.js.nowakelock.data.provider

import android.content.Context
import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.WakeLock
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProviderHandler(
    context: Context
) {
    private val TAG = "ProviderHandler"

    private var db: AppDatabase = AppDatabase.getInstance(context)

    private val wls = "WakeLockList"
//    private val packageName = "PackageName"
//    private val wakelockName = "WakelockName"
//    private val flaG = "Flag"

    companion object {
        @Volatile
        private var instance: ProviderHandler? = null

        fun getInstance(context: Context): ProviderHandler =
            instance ?: ProviderHandler(context)
    }


    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return when (methodName) {
            "saveWLS" -> saveWLS(bundle)
//            "getFlag" -> getFlag(bundle)
//            "upCount" -> upCount(bundle)
//            "upBlockCount" -> upBlockCount(bundle)
            "test" -> test(bundle)
            else -> null
        }
    }

    private fun wls(bundle: Bundle) = bundle.get(wls) as MutableCollection<WakeLock>?

//    private fun pN(bundle: Bundle) = bundle.get(packageName) as String?
//    private fun wN(bundle: Bundle) = bundle.get(wakelockName) as String?
//    private fun fL(bundle: Bundle) = bundle.get(flaG) as Boolean?

    //update db wakelock
    @Synchronized
    private fun saveWLS(bundle: Bundle): Bundle? {
        val wls = wls(bundle)
        wls?.let {
            GlobalScope.launch {
                try {
                    it.forEach {
                        val tmp = db.wakeLockDao().loadWakeLock(it.wakeLockName)
                        tmp.count += it.count
                        tmp.countTime += it.countTime
                        tmp.blockCount += it.blockCount
                        tmp.blockCountTime += it.blockCountTime
                        db.wakeLockDao().insert(tmp)
                    }
                } catch (e: Exception) {
                    LogUtil.d(TAG, e.toString())
                }
            }
        }
        return null
    }

    //    private fun getFlag(bundle: Bundle): Bundle? {
//        val flag = serviceModel.getFlag(pN(bundle), wN(bundle))
//        val tmp = Bundle()
//        tmp.putBoolean(flaG, flag)
//        return tmp
//    }
//
//    @Synchronized
//    private fun upCount(bundle: Bundle): Bundle? {
//        serviceModel.upCount(pN(bundle), wN(bundle))
//        return null
//    }
//
//    @Synchronized
//    private fun upBlockCount(bundle: Bundle): Bundle? {
//        serviceModel.upBlockCount(pN(bundle), wN(bundle))
//        return null
//    }
//
    @VisibleForTesting
    fun test(bundle: Bundle): Bundle? {
//        val pn = pN(bundle)
//        val wn = wN(bundle)
//        val flag = fL(bundle)

        val test = bundle.get("Test") as String?

        LogUtil.d(TAG, "$test")

        val tmp = Bundle()
        tmp.putString("Test", "Test")
        return tmp
    }
}