package com.js.nowakelock.xposedhook

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Bundle
import de.robv.android.xposed.XposedBridge

//print string
fun log(string: String) = XposedBridge.log(string)


class cR(context: Context) {

    companion object {
        @Volatile
        private var instance: cR? = null
        fun getInstance(context: Context): cR =
            instance ?: synchronized(this) {
                instance ?: cR(context).also {
                    instance = it
                }
            }
    }

    private var mContentResolver: ContentResolver = context.contentResolver

    //ContentProvider
    private val authority = "com.js.nowakelock"
    private val url = Uri.parse("content://$authority")

    private val packageName_cr = "PackageName"
    private val wakelockName_cr = "WakelockName"
    private val flaG = "Flag"

    //method
    private val run = "run"
    private val getFlag = "getFlag"
    private val upCount = "upCount"
    private val upBlockCount = "upBlockCount"

    fun run(pN: String): Boolean {
        var bundle: Bundle? = null
        try {
            val extras = Bundle()
            extras.putBoolean(run, true)
            bundle = mContentResolver.call(url, run, null, extras)
        } catch (e: Exception) {
            log("$TAG $pN: run err $e")
        }
        log("$TAG $pN: run $bundle")
        log("$TAG $pN: run ${bundle?.getBoolean(run, false)}")
        return bundle?.getBoolean(run, false) ?: false
    }


    fun getFlag(packageName: String, wakelockName: String): Boolean {
        var bundle: Bundle? = null
        try {
            val extras = Bundle()
            extras.let {
                it.putString(packageName_cr, packageName)
                it.putString(wakelockName_cr, wakelockName)
            }
            bundle = mContentResolver.call(url, getFlag, null, extras)
        } catch (e: Exception) {
            log("Xposed.NoWakeLock: err $e")
        }
        // default true
        return bundle?.getBoolean(flaG) ?: true
    }

    fun upCount(packageName: String, wakelockName: String) {
        try {
            val extras = Bundle()
            extras.let {
                it.putString(packageName_cr, packageName)
                it.putString(wakelockName_cr, wakelockName)
            }
            mContentResolver.call(url, upCount, null, extras)
        } catch (e: Exception) {
            log("Xposed.NoWakeLock: err $e")
        }
    }

    fun upBlockCount(packageName: String, wakelockName: String) {
        try {
            val extras = Bundle()
            extras.let {
                it.putString(packageName_cr, packageName)
                it.putString(wakelockName_cr, wakelockName)
            }
            mContentResolver.call(url, upBlockCount, null, extras)
        } catch (e: Exception) {
            log("Xposed.NoWakeLock: err $e")
        }
    }
}

