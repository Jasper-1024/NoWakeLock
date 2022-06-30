package com.js.nowakelock.xposedhook

import de.robv.android.xposed.XposedBridge


object XpUtil {
    private const val Tag = "Xposed.NoWakeLock"
    const val authority = "com.js.nowakelock"

    private var log = true

    fun log(string: String) {
        if (log) {
            XposedBridge.log("$Tag: $string")
        }
    }
}