package com.js.nowakelock.xposedhook

import de.robv.android.xposed.XposedBridge

val TAG = "Xposed.NoWakeLock"
val authority = "com.js.nowakelock"

fun log(string: String) {
    if (true) {
        XposedBridge.log("$TAG: $string")
    }
}