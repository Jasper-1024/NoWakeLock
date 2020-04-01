package com.js.nowakelock.service.provider

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.service.ServiceModel

class ProviderHandler(
    private val serviceModel: ServiceModel
) {
    private val packageName = "PackageName"
    private val wakelockName = "WakelockName"
    private val flaG = "Flag"
    private val run = "run"

    companion object {
        @Volatile
        private var instance: ProviderHandler? = null

        fun getInstance(serviceModel: ServiceModel): ProviderHandler =
            instance ?: ProviderHandler(serviceModel)
    }


    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return when (methodName) {
            "getFlag" -> getFlag(bundle)
            "upCount" -> upCount(bundle)
            "upBlockCount" -> upBlockCount(bundle)
            "run" -> run(bundle)
            "test" -> test(bundle)
            else -> null
        }
    }

    private fun pN(bundle: Bundle) = bundle.get(packageName) as String?
    private fun wN(bundle: Bundle) = bundle.get(wakelockName) as String?
    private fun fL(bundle: Bundle) = bundle.get(flaG) as Boolean?

    private fun getFlag(bundle: Bundle): Bundle? {
        val flag = serviceModel.getFlag(pN(bundle), wN(bundle))
        val tmp = Bundle()
        tmp.putBoolean(flaG, flag)
        return tmp
    }

    @Synchronized
    private fun upCount(bundle: Bundle): Bundle? {
        serviceModel.upCount(pN(bundle), wN(bundle))
        return null
    }

    @Synchronized
    private fun upBlockCount(bundle: Bundle): Bundle? {
        serviceModel.upBlockCount(pN(bundle), wN(bundle))
        return null
    }

    private fun run(bundle: Bundle): Bundle? {
        LogUtil.d("Xposed.NoWakeLock", "run")
        val tmp = Bundle()
        tmp.putBoolean(run, true)
        return tmp
    }

    @VisibleForTesting
    fun test(bundle: Bundle): Bundle? {
        val pn = pN(bundle)
        val wn = wN(bundle)
        val flag = fL(bundle)

        LogUtil.d("test1", "$pn,$wn,$flag")

        val tmp2 = serviceModel.test(pn, wn)

        val tmp = Bundle()
        tmp.let {
            it.putString(packageName, pn)
            it.putString(wakelockName, wn)
            it.putBoolean(flaG, tmp2)
        }
        return tmp
    }
}