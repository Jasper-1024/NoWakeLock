package com.js.nowakelock.xposedhook

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import de.robv.android.xposed.XposedBridge

val TAG = "Xposed.NoWakeLock"
val authority = "com.js.nowakelock"

//fun log(string: String) {
//    if (true) {
//        XposedBridge.log("$TAG: $string")
//    }
//}

object XpUtil {
    val authority = "com.js.nowakelock"
    fun log(string: String) {
        if (false) {
            XposedBridge.log("$TAG: $string")
        }
    }

    fun record(method: String, bundle: Bundle, context: Context) {
        val url = Uri.parse("content://$authority")
        val contentResolver = context.contentResolver
        try {
            contentResolver.call(url, method, null, bundle)
        } catch (e: Exception) {
            log(": record $method err: $e")
        }
    }

    fun rE(wN: String, rE: Set<String>): Boolean {
        if (rE.isEmpty()) {
            return true
        } else {
            rE.forEach {
                if (wN.matches(Regex(it))) {
                    return false
                }
            }
            return true
        }
    }

    fun aTI(lastAllowTime: Long, aTI: Long): Boolean {
        return (SystemClock.elapsedRealtime() - lastAllowTime) >= aTI
    }
}