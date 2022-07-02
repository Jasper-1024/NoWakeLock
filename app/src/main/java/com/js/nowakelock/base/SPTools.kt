package com.js.nowakelock.base

import android.annotation.SuppressLint
import android.content.Context
import com.js.nowakelock.BasicApp


class SPTools {
    companion object {
        const val SP_NAME = "Nowakelock"

        private const val default_str = ""
        private const val default_bool = true

        @SuppressLint("WorldReadableFiles")
        private var prefs = try {
            // MODE_WORLD_READABLE, New XSharedPreferences
            BasicApp.context.getSharedPreferences(SP_NAME, Context.MODE_WORLD_READABLE)
        } catch (e: SecurityException) {
            null
        }

        fun getString(key: String, defaultValue: String = default_str): String {

            return prefs?.getString(key, defaultValue) ?: defaultValue
        }

        fun setString(key: String, value: String) {
            with(prefs?.edit() ?: return) {
                putString(key, value)
//                apply()
                commit()
            }
        }

        fun getBoolean(key: String, defaultValue: Boolean = default_bool): Boolean {
            return prefs?.getBoolean(key, defaultValue) ?: defaultValue
        }

        fun setBoolean(key: String, value: Boolean) {
            with(prefs?.edit() ?: return) {
                putBoolean(key, value)
//                apply()
                commit()
            }
        }

        fun getInt(key: String, defaultValue: Int = 0): Int {
            return prefs?.getInt(key, defaultValue) ?: defaultValue
        }

        fun setInt(key: String, value: Int) {
            with(prefs?.edit() ?: return) {
                putInt(key, value)
                commit()
            }
        }

        fun getLong(key: String, defaultValue: Long = 0): Long {
            return prefs?.getLong(key, defaultValue) ?: defaultValue
        }

        fun setLong(key: String, value: Long) {
            with(prefs?.edit() ?: return) {
                putLong(key, value)
                commit()
            }
        }
    }
}