package com.js.nowakelock.xposedhook.wakelock

import android.content.Context

interface WLModel {
    fun reloadst()
    fun reloadst(context: Context)
    fun getFlag(wN: String): Boolean
    fun getAllowTimeinterval(wN: String): Long
    fun getRe(pN: String): Set<String>
}