package com.js.nowakelock.xposedhook

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.AsyncTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class XpCR(context: Context) {

    private val TAG = "Xposed.NoWakeLock"

    companion object {
        @Volatile
        private var instance: XpCR? = null
        fun getInstance(context: Context): XpCR =
            instance ?: synchronized(this) {
                instance ?: XpCR(context).also {
                    instance = it
                }
            }
    }

    private var mContentResolver: ContentResolver = context.contentResolver

    private val authority = "com.js.nowakelock"
    val PackageName = "PackageName"
    val WakelockName = "WakelockName"


    @SuppressLint("Recycle")
    fun getFlag(packageName: String, wakelockName: String): Boolean {
        val url = Uri.parse("content://$authority/flag")
        val tmp = mContentResolver.query(url, arrayOf(""), wakelockName, null, null)
        log(tmp.toString())
        return true
    }

    fun upCount(packageName: String, wakelockName: String) {
//        AsyncTask.execute {
        GlobalScope.launch(Dispatchers.Default) {
            val url = Uri.parse("content://$authority/upCount")
            val newValues = ContentValues().apply {
                put(PackageName, packageName)
                put(WakelockName, wakelockName)
            }
            withContext(Dispatchers.IO) {
                try {
                    val tmp = mContentResolver.insert(url, newValues)
                    if (tmp == url) {
                        log("$TAG $packageName: $wakelockName upCount ")
                    }
                } catch (e: Exception) {
                    log("$TAG $packageName: $wakelockName upCount Err : $e ")
                }
            }
        }
//        }
    }

    fun upBlockCount(packageName: String, wakelockName: String) {
        AsyncTask.execute {
            val url = Uri.parse("content://$authority/upBlockCount")
            val newValues = ContentValues().apply {
                put(PackageName, packageName)
                put(WakelockName, wakelockName)
            }
            try {
                val tmp = mContentResolver.insert(url, newValues)
                if (tmp == url) {
                    log("$TAG $packageName: $wakelockName upBlockCount ")
                }
            } catch (e: Exception) {
                log("$TAG $packageName: $wakelockName upBlockCount Err : $e ")
            }
        }
    }

}