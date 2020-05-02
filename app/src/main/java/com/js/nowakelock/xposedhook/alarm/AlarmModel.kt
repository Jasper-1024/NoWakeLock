package com.js.nowakelock.xposedhook.alarm

import android.content.Context

interface AlarmModel {
    fun reloadst(context: Context)
    fun getFlag(wN: String): Boolean
    fun getRe(pN: String): Set<String>
}