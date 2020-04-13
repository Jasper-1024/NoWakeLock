package com.js.nowakelock.base

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone

class TimeUtil {
    companion object {
        private val formatter = SimpleDateFormat("HH:mm:ss")
        fun getTime(time: Long): String {
            formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
            return formatter.format(time)
        }
    }
}