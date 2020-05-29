package com.js.nowakelock.ui.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.Alarm
import com.js.nowakelock.data.db.entity.Alarm_st
import com.js.nowakelock.data.repository.AlarmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator


class AlarmViewModel(
    packageName: String = "",
    var repository: AlarmRepository
) : ViewModel() {
    var list: LiveData<List<Alarm>> =
        if (packageName == "") repository.getAlarms()
        else repository.getAlarms(packageName)

    //save alarm flag
    fun setFlag(alarm: Alarm) = viewModelScope.launch(Dispatchers.IO) {
        repository.setAlarm_st(
            Alarm_st(alarm.name, alarm.flag.get(), alarm.allowTimeinterval)
        )
    }

    suspend fun list(alarms: List<Alarm>, catch: cache): List<Alarm> =
        withContext(Dispatchers.Default) {
            return@withContext alarms.flag()
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    // get all Alarm flag
    suspend fun List<Alarm>.flag(): List<Alarm> {
        return this.map {
            val tmp = repository.getAlarm_st(it.name)
            it.flag.set(tmp.flag)
            it.allowTimeinterval = tmp.allowTimeinterval
            it
        }
    }

    fun search(alarm: Alarm) = alarm.name

    // get sort method
    fun sort(sort: Int): java.util.Comparator<Alarm> {
        return when (sort) {
            1 -> Comparator<Alarm> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.name, s2.name)
            }
            2 -> compareByDescending<Alarm> { it.count }
            3 -> compareByDescending<Alarm> { it.countTime }
            else -> Comparator<Alarm> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.name, s2.name)
            }
        }
    }
}