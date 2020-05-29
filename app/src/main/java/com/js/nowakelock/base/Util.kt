package com.js.nowakelock.base

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.view.Menu
import java.util.*

//Long to Time
@SuppressLint("SimpleDateFormat")
private val formatter = SimpleDateFormat("mm:ss")
fun getTime(time: Long): String {
    formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
    return formatter.format(time)
//    return (time/1000).toString()
}

fun menuGone(menu: Menu, set: Set<Int>) {
    set.forEach {
        val filterUser = menu.findItem(it)
        filterUser.isVisible = false
    }
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

object Util {
    @JvmStatic
    fun stringToSet(value: String?): Set<String>? {
        return if (value == null || value == "") {
            mutableSetOf()
        } else {
            value.split("\n")
                .filter { it.matches(Regex("[^\n ]+")) }
                .toSet()
//            value.split('\n').toSet()
        }
    }

    @JvmStatic
    fun setToString(values: Set<String>?): String? {
        return if (values == null || values.isEmpty()) {
            ""

        } else {
            var tmp = ""
            values.forEach {
                tmp += "$it\n"
            }
//            tmp = tmp.substring(0, tmp.length - 1)
            tmp
        }
    }
}
