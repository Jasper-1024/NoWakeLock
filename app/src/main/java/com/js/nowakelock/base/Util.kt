package com.js.nowakelock.base

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.view.Menu
import android.widget.Toast
import com.js.nowakelock.BasicApp
import com.js.nowakelock.R
import com.js.nowakelock.data.db.Type
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
    val q = query.lowercase(Locale.ROOT).trim { it <= ' ' }
    if (q == "") {
        return this
    }
    return this.filter {
        text(it).lowercase(Locale.ROOT).contains(q)
    }
}

// sort list
fun <T : BaseItem> List<T>.sort(comparator: Comparator<in T>): List<T> {
    return this.sortedWith(comparator)
}

fun clipboardCopy(str: String): Boolean {
    val context = BasicApp.context
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip: ClipData = ClipData.newPlainText("", str)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$str ${context.getString(R.string.clipboard)}", Toast.LENGTH_LONG)
        .show()
    return true
}

object Util {
    @JvmStatic
    fun stringToSet(value: String?): Set<String> {
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
    fun setToString(values: Set<String>?): String {
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

fun stringToType(value: String): Type {
    return when (value) {
        "Wakelock" -> Type.Wakelock
        "Alarm" -> Type.Alarm
        "Service" -> Type.Service
        else -> Type.UnKnow
    }
}

fun typeToString(type: Type): String {
    return type.value
}

