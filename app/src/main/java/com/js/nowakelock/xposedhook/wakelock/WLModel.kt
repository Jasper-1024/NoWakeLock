package com.js.nowakelock.xposedhook.wakelock

interface WLModel {
    fun init()
    fun getFlag(wN: String): Boolean
    fun getAllowTimeinterval(wN: String): Long
    fun getRe(pN: String): String
}