package com.js.nowakelock.base

import android.os.Bundle
import com.js.nowakelock.data.db.entity.WakeLock

class WLUtil {
    companion object {
        private val packageName = "PackageName"
        private val wakelockName = "WakelockName"
        private val uid = "Uid"
        private val count = "Count"
        private val blockCount = "BlockCount"
        private val countTime = "CountTime"
        private val blockCountTime = "BlockCountTime"

        private fun wN(bundle: Bundle) = bundle.get(wakelockName) as String
        private fun pN(bundle: Bundle) = bundle.get(packageName) as String
        private fun uid(bundle: Bundle) = bundle.get(uid) as Int
        private fun count(bundle: Bundle) = bundle.get(count) as Int
        private fun blockCount(bundle: Bundle) = bundle.get(blockCount) as Int
        private fun countTime(bundle: Bundle) = bundle.get(countTime) as Long
        private fun blockCountTime(bundle: Bundle) = bundle.get(blockCountTime) as Long

        fun getBundle(wakeLock: WakeLock): Bundle {
            val tmp = Bundle()
            tmp.let {
                it.putString(wakelockName, wakeLock.wakeLockName)
                it.putString(packageName, wakeLock.packageName)
                it.putInt(uid, wakeLock.uid)
                it.putInt(count, wakeLock.count)
                it.putInt(blockCount, wakeLock.blockCount)
                it.putLong(countTime, wakeLock.countTime)
                it.putLong(blockCountTime, wakeLock.blockCountTime)
            }
            return tmp
        }

        fun getWakeLock(bundle: Bundle): WakeLock {
            val tmp = WakeLock()
            tmp.let {
                it.wakeLockName = wN(bundle)
                it.packageName = pN(bundle)
                it.uid = uid(bundle)
                it.count = count(bundle)
                it.blockCount = blockCount(bundle)
                it.countTime = countTime(bundle)
                it.blockCountTime = blockCountTime(bundle)
            }
            return tmp
        }
    }
}