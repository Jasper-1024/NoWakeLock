package com.js.nowakelock.xposedhook

import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers


object XpUtil {
    private const val Tag = "Xposed.NoWakeLock"
    const val authority = "com.js.nowakelock"

    private var log = true

    fun log(string: String) {
        if (log) {
            XposedBridge.log("$Tag: $string")
        }
    }

    fun getClass(name: String, classLoader: ClassLoader): Class<*>? {
        return try {
            XposedHelpers.findClass(name, classLoader)
        } catch (e: Throwable) {
            log("alarm getClass err: $e")
            null
        }
    }
}