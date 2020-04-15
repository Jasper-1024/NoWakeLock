package com.js.nowakelock.xposedhook

import com.js.nowakelock.BuildConfig
import de.robv.android.xposed.XSharedPreferences
import de.robv.android.xposed.XposedBridge
import java.io.File

val TAG = "Xposed.NoWakeLock"

fun log(string: String) = XposedBridge.log(string)

class XpUtil {
    companion object {
        private var pref: XSharedPreferences? = null
        private val prefsFileProt =
            File("/data/user_de/0/${BuildConfig.APPLICATION_ID}/shared_prefs/${BuildConfig.APPLICATION_ID}_preferences.xml")

        val authority = "com.js.nowakelock"

        fun init() {
            load()
        }

        fun getFlag(wN: String): Boolean {
            if (pref == null) {
                load()
            }
            reload()
            log("$TAG: $wN getFlag ${pref!!.getBoolean(wN, true)}")
            return pref!!.getBoolean(wN, true)
        }

        fun getRe(pN: String): String? {
            if (pref == null) {
                load()
            }
            return pref!!.getString("${pN}_RE", "")
        }

        fun reload() {
            try {
                pref?.reload()
            } catch (e: Exception) {
                log("$TAG : XpUtil pref reload err: $e")
            }
        }

        fun load() {
            try {
                pref = XSharedPreferences(prefsFileProt)
                log("$TAG ")
//                pref!!.reload()
            } catch (t: Throwable) {
                log("$TAG : XpUtil pref err: $t")
            }
        }

    }
}
