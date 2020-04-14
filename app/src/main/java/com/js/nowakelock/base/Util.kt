package com.js.nowakelock.base

import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import java.util.*
import kotlin.Comparator

//Long to Time
private val formatter = SimpleDateFormat("HH:mm:ss")
fun getTime(time: Long): String {
    formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
    return formatter.format(time)
}

//status
data class cache(
    var app: Int = 1,
    var sort: Int = 1,
    var query: String = ""
)

// filter list
inline fun <T : BaseItem> List<T>.app(status: (T) -> Boolean): List<T> {
    return this.filter { status(it) }
}

// search list
inline fun <T : BaseItem> List<T>.search(query: String, text: (T) -> String): List<T> {
    /*lowerCase and no " " */
    val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
    if (q == "") {
        return this
    }
    return this.filter {
        text(it).toLowerCase(Locale.ROOT).contains(q)
    }
}

// sort list
fun <T : BaseItem> List<T>.sort(comparator: Comparator<in T>): List<T> {
    return this.sortedWith(comparator)
}