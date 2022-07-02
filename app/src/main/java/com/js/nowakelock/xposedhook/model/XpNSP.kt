package com.js.nowakelock.xposedhook.model

import android.content.SharedPreferences
import com.js.nowakelock.BuildConfig
import com.js.nowakelock.base.SPTools
import com.js.nowakelock.data.db.Type
import de.robv.android.xposed.XSharedPreferences

class XpNSP() {

    @Volatile
    private var pref: SharedPreferences? = null

    companion object {

        @Volatile
        private var instance: XpNSP? = null

        fun getInstance() = instance ?: synchronized(this) {
            XpNSP().also {
                it.getPref()
                instance = it
            }
        }
    }

    private fun getPref() = pref ?: synchronized(this) {
        val p = XSharedPreferences(BuildConfig.APPLICATION_ID, SPTools.SP_NAME)
        pref = if (p.file.canRead()) p else null
    }


    fun flag(name: String, packageName: String, type: Type): Boolean {
        return getBool("${name}_${type}_${packageName}_flag")
    }

    fun aTI(
        now: Long, lastActive: Long,
        name: String, packageName: String, type: Type
    ): Boolean {
        val ati = getLong("${name}_${type}_${packageName}_aTI")

        return (now - lastActive) < ati
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
        return pref?.getBoolean(key, defValue) ?: defValue
    }

    private fun getLong(key: String, defValue: Long = 0): Long {
        return pref?.getLong(key, defValue) ?: defValue
    }

    private fun getSet(key: String): Set<String> {
        return pref?.getStringSet(key, emptySet()) ?: emptySet()
    }
}