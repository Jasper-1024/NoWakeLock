package com.js.nowakelock.xposedhook.alarm

import android.content.Context
import android.net.Uri
import android.os.Bundle
import com.js.nowakelock.xposedhook.authority
import com.js.nowakelock.xposedhook.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class mAlarmModel : AlarmModel {
    @Volatile
    var alFlagHM: HashMap<String, Boolean> = HashMap<String, Boolean>() //flag

    @Volatile
    var alReHM: HashMap<String, Set<String>> = HashMap<String, Set<String>>() //re


    private val getALStHM = "getALStHM"
    private val alFlag = "alFlagHM"
    private val alRe = "alReHM"


    override fun reloadst(context: Context) {
        GlobalScope.launch(Dispatchers.Default) {
            try {
                val bundle = getBundle(getALStHM, context)
                if (bundle != null) {
                    loadSt(bundle)
                    loadRe(bundle)
                }
            } catch (e: java.lang.Exception) {
                log("alarm reloadst err: $e")
            }
        }
    }

    override fun getFlag(aN: String): Boolean {
        return alFlagHM[aN] ?: true
    }

    override fun getRe(pN: String): Set<String> {
        return alReHM[pN] ?: mutableSetOf()
    }


    private suspend fun getBundle(method: String, context: Context): Bundle? =
        withContext(Dispatchers.IO) {
            val url = Uri.parse("content://${authority}")
            val contentResolver = context.contentResolver
            return@withContext try {
                val tmp = contentResolver.call(url, method, null, Bundle())
                tmp
            } catch (e: Exception) {
                null
            }
        }

    private fun loadSt(bundle: Bundle) {
        val tmp1 = bundle.getSerializable(alFlag) as HashMap<String, Boolean>?
        tmp1?.let {
            alFlagHM.putAll(it)
        }
    }

    private fun loadRe(bundle: Bundle) {
        val tmp = bundle.getSerializable(alRe) as HashMap<String, Set<String>>?
        tmp?.let {
            alReHM.putAll(it)
        }
    }

}