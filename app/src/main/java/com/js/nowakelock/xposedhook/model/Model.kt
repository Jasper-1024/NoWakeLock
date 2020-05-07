package com.js.nowakelock.xposedhook.model

import android.content.Context

interface Model {
    fun handleTimer(context: Context)

    fun flag(name: String): Boolean
    fun aTi(name: String, lastAllowTime: Long): Boolean
    fun re(name: String, packageName: String): Boolean

    fun upCount(name: String)
    fun upBlockCount(name: String)

//    fun upCountTime(name: String, time: Long)
//    fun upBlockCountTime(name: String, time: Long)
}