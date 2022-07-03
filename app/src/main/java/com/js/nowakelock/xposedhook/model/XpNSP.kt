package com.js.nowakelock.xposedhook.model

import android.os.SystemClock
import com.js.nowakelock.BuildConfig
import com.js.nowakelock.base.SPTools
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.xposedhook.XpUtil
import de.robv.android.xposed.XSharedPreferences

class XpNSP() {

    @Volatile
    var pref: XSharedPreferences? = null

    @Volatile
    private var lastRefresh: Long = 0

    companion object {

        @Volatile
        private var instance: XpNSP? = null

        fun getInstance() = instance ?: synchronized(this) {
            XpNSP().also {
                it.makePref()
                it.reFresh()
                instance = it
            }
        }
    }

    fun makePref(): XSharedPreferences? {
        return pref ?: synchronized(this) {
            val p = XSharedPreferences(BuildConfig.APPLICATION_ID, SPTools.SP_NAME)
            pref = if (p.file.canRead()) p else null
            pref
        }
    }

    fun flag(name: String, packageName: String, type: Type): Boolean {
        return getBool("${name}_${type}_${packageName}_flag")
    }

    fun aTI(
        now: Long, lastActive: Long,
        name: String, packageName: String, type: Type
    ): Boolean {
        val ati = getLong("${name}_${type}_${packageName}_aTI")

        return (now - lastActive) < (ati * 1000)
    }

    fun rE(name: String, packageName: String, type: Type): Boolean {
        val rE = getSet("$${type}_${packageName}_rE")
        if (rE.isEmpty()) {
            return false
        } else {
            rE.forEach {
                if (name.matches(Regex(it))) {
                    return true
                }
            }
            return false
        }
    }


    private fun getBool(key: String, defValue: Boolean = false): Boolean {
        reFresh()
        return pref?.getBoolean(key, defValue) ?: defValue
    }

    private fun getLong(key: String, defValue: Long = 0): Long {
        reFresh()
        return pref?.getLong(key, defValue) ?: defValue
    }

    private fun getSet(key: String): Set<String> {
        reFresh()
        return pref?.getStringSet(key, emptySet()) ?: emptySet()
    }

    fun reFresh() {
        if (SystemClock.elapsedRealtime() - lastRefresh > (30 * 1000)) {
            XpUtil.log("pref reFresh ${SystemClock.elapsedRealtime()}")
            pref = makePref()
            pref?.reload()
            lastRefresh = SystemClock.elapsedRealtime()
        }
    }
}