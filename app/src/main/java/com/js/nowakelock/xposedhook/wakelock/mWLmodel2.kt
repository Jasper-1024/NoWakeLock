package com.js.nowakelock.xposedhook.wakelock

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.js.nowakelock.xposedhook.TAG
import com.js.nowakelock.xposedhook.authority
import com.js.nowakelock.xposedhook.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class mWLmodel2 : WLModel {
    @Volatile
    var wlFlagHM: HashMap<String, Boolean> = HashMap<String, Boolean>() //flag

    @Volatile
    var wlATIHM: HashMap<String, Long> = HashMap<String, Long>() // allow time interval

    @Volatile
    var reHM: HashMap<String, String> = HashMap<String, String>() //re

    private val wlSt = "wlStsHM"
    private val wlFlags = "wlFlagHM"
    private val wlATIs = "wlwlATIHM"
    private val rE = "rEHM"

    override fun reloadst(context: Context) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val wlsB = getBundle(wlSt, context)
//                val rEB = getBundle(rE,context)
                if (wlsB != null) {
                    loadSt(wlsB)
                }
            } catch (e: java.lang.Exception) {
                log("$TAG : mWLmodel2 reloadst err: $e")
            }
        }
    }

    override fun getFlag(wN: String): Boolean {
        return wlFlagHM[wN] ?: true
    }

    override fun getAllowTimeinterval(wN: String): Long {
        return wlATIHM[wN] ?: 0
    }

    override fun getRe(pN: String): String {
        return reHM[pN] ?: ""
    }

    override fun reloadst() {}


    private fun loadSt(bundle: Bundle) {
        val tmp1 = bundle.getSerializable(wlFlags) as HashMap<String, Boolean>?
//        log("$TAG : mWLmodel2 reloadst1.6 : ${tmp1}")
        tmp1?.let {
            wlFlagHM.putAll(it)
//            log("$TAG : mWLmodel2 reloadst1.7 : ${wlFlagHM}")
        }
        val tmp2 = bundle.getSerializable(wlATIs) as HashMap<String, Long>?
//        log("$TAG : mWLmodel2 reloadst1.8 : ${tmp2}")
        tmp2?.let {
            wlATIHM.putAll(it)
//            log("$TAG : mWLmodel2 reloadst1.9 : ${wlATIHM}")
        }
//        log("$TAG : mWLmodel2 reloadst1.6 : ${tmp3}")
    }

    private fun loadRe(bundle: Bundle) {
        val tmp = bundle.getSerializable(rE) as HashMap<String, String>?
        tmp?.let {
            reHM.putAll(it)
        }
    }

    private suspend fun getBundle(method: String, context: Context): Bundle? =
        withContext(Dispatchers.IO) {
            val url = Uri.parse("content://${authority}")
            val contentResolver = context.contentResolver
            return@withContext try {
                val tmp = contentResolver.call(url, method, null, Bundle())
//                log("$TAG : Bundle3 : $tmp")
                tmp
            } catch (e: Exception) {
                log("$TAG : getBundle $method err: $e")
                null
            }
        }
}