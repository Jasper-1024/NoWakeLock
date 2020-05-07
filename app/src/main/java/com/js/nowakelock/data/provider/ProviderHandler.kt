package com.js.nowakelock.data.provider

import android.content.Context
import android.os.Bundle
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.xposedhook.model.DB
import com.js.nowakelock.xposedhook.model.DBModel
import com.js.nowakelock.xposedhook.model.STModel
import com.js.nowakelock.xposedhook.model.XPM

class ProviderHandler(
    context: Context
) {
    private val TAG = "ProviderHandler"

    private var db: AppDatabase = AppDatabase.getInstance(context)

    companion object {
        @Volatile
        private var instance: ProviderHandle? = null

        fun getInstance(context: Context): ProviderHandle {
            if (instance == null) {
                instance = ProviderHandle(context)
            }
            return instance!!
        }
    }

    fun getMethod(methodName: String, bundle: Bundle): Bundle? {
        return null
    }

    fun getType(bundle: Bundle): String {
        return bundle.getString(XPM.type) ?: ""
    }

    private fun db(bundle: Bundle): Bundle? {
        val tmp = getDBModel(bundle)
        return dbMethod(getType(bundle))(tmp.dbHM.values)
    }

    private fun getDBModel(bundle: Bundle): DBModel {
        val tmp = bundle.getSerializable(XPM.db) as DBModel
        return tmp
    }

    private fun dbMethod(type: String): (MutableCollection<DB>) -> Bundle? {
        return when (type) {
            XPM.alarm -> ::dbAlarm
            XPM.service -> ::dbService
            XPM.wakelock -> ::dbWakelock
            else -> ::update
        }
    }

    private fun dbAlarm(list: MutableCollection<DB>): Bundle? {
        return null
    }

    private fun dbWakelock(list: MutableCollection<DB>): Bundle? {
        return null
    }

    private fun dbService(list: MutableCollection<DB>): Bundle? {
        return null
    }

    private fun update(list: MutableCollection<DB>): Bundle? {
        return null
    }

    private fun st(bundle: Bundle): Bundle? {
        return null
    }

    private fun stMethod(type: String): () -> STModel {
        return when (type) {
            XPM.alarm -> ::stAlarm
            XPM.service -> ::stService
            XPM.wakelock -> ::stWakelock
            else -> ::st
        }
    }

    private fun stAlarm(): STModel {
        TODO()
    }

    private fun stService(): STModel {
        TODO()
    }

    private fun stWakelock(): STModel {
        TODO()
    }

    private fun st(): STModel {
        TODO()
    }
}