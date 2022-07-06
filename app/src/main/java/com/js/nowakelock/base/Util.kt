package com.js.nowakelock.base

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.icu.util.TimeZone
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.js.nowakelock.BasicApp
import com.js.nowakelock.R
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.db.entity.Info
import com.js.nowakelock.data.provider.getURI
import java.util.*

//Long to Time
@SuppressLint("SimpleDateFormat")
private val formatter = SimpleDateFormat("mm:ss")
fun getTime(time: Long): String {
    formatter.timeZone = TimeZone.getTimeZone("GMT+00:00")
    return formatter.format(time)
//    return (time/1000).toString()
}

// filter list
inline fun <T : Any> List<T>.appType(status: (T) -> Boolean): List<T> {
    return this.filter { status(it) }
}

fun menuGone(menu: Menu, set: Set<Int>) {
    set.forEach {
        val filterUser = menu.findItem(it)
        filterUser.isVisible = false
    }
}

// search list
inline fun <T : Any> List<T>.search(query: String, text: (T) -> String): List<T> {
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
fun <T : Any> List<T>.sort(comparator: Comparator<in T>): List<T> {
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

fun bundleToInfo(bundle: Bundle): Info {
    return Info(
        bundle.getString("name") ?: "",
        stringToType(bundle.getString("package") ?: ""),
        bundle.getString("packageName") ?: "",
        bundle.getInt("count"),
        bundle.getInt("blockCount"),
        bundle.getLong("countTime")
    )
}

fun infoToBundle(info: Info): Bundle {
    return Bundle().apply {
        putString("name", info.name)
        putString("package", typeToString(info.type))
        putString("packageName", info.packageName)
        putInt("count", info.count)
        putInt("blockCount", info.blockCount)
        putLong("countTime", info.countTime)
    }
}

/**
 * Call XP ContentProvider
 * @param context Context
 * @param args Bundle
 * @param method String
 * @return Bundle?
 */
fun getCPResult(context: Context, method: String, args: Bundle): Bundle? {
    val contentResolver = context.contentResolver
    return contentResolver.call(getURI(), "NoWakelock", method, args)
}

/**
 * transform seq to string
 * @param paramTimes Array<out Any>
 * @return String
 */
fun getFormattedTime(vararg paramTimes: Any): String {
    val sBuff = StringBuilder()
    var offset = 0
    if (5 == paramTimes.size) {
        offset = 2
    } else if (4 == paramTimes.size) {
        offset = 1
    }
    for (i in paramTimes.indices) {
        if (i < paramTimes.size - offset) {
            if (paramTimes[i] as Long > 0) {
                sBuff.append(String.format("%02d", paramTimes[i]))
                sBuff.append(":")
            } else {
                continue
            }
        } else {
            sBuff.append(String.format("%02d", paramTimes[i]))
            sBuff.append(":")
        }
    }
    sBuff.deleteCharAt(sBuff.lastIndexOf(":"))
    return sBuff.toString()
}

