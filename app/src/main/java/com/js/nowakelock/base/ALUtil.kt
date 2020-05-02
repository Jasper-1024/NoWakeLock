package com.js.nowakelock.base

import android.os.Bundle
import com.js.nowakelock.data.db.entity.Alarm

class ALUtil {
    companion object {
        private val packageName = "PackageName"
        private val alarmName = "AlarmName"
        private val count = "Count"
        private val blockCount = "BlockCount"

        private fun aN(bundle: Bundle) = bundle.get(alarmName) as String
        private fun pN(bundle: Bundle) = bundle.get(packageName) as String
        private fun count(bundle: Bundle) = bundle.get(count) as Int
        private fun blockCount(bundle: Bundle) = bundle.get(blockCount) as Int

        fun getBundle(alarm: Alarm): Bundle {
            val tmp = Bundle()
            tmp.let {
                it.putString(alarmName, alarm.alarmName)
                it.putString(packageName, alarm.packageName)
                it.putInt(count, alarm.count)
                it.putInt(blockCount, alarm.blockCount)
            }
            return tmp
        }

        fun getAlarm(bundle: Bundle): Alarm {
            val tmp = Alarm()
            tmp.let {
                it.alarmName = aN(bundle)
                it.packageName = pN(bundle)
                it.count = count(bundle)
                it.blockCount = blockCount(bundle)
            }
            return tmp
        }
    }
}