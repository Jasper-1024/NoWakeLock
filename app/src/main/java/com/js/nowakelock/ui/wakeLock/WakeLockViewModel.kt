package com.js.nowakelock.ui.wakeLock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.repository.WakeLockRepository
import com.js.nowakelock.data.sp.SP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*

class WakeLockViewModel(
    private var packageName: String,
    private var WakeLockRepository: WakeLockRepository
) : ViewModel() {

    val TAG = "WakeLockViewModel"

    var wakeLocks: LiveData<List<WakeLock>> = WakeLockRepository.getWakeLocks(packageName)

    fun syncWakeLocks() = viewModelScope.launch {
//        LogUtil.d("test1",packageName)
        WakeLockRepository.syncWakelocks(packageName)
    }

    //save wakelock flag
    fun setWakeLockFlag(wakeLock: WakeLock) = viewModelScope.launch(Dispatchers.IO) {
        SP.getInstance().setFlag(wakeLock.wakeLockName, wakeLock.flag)
    }

    suspend fun WakeLockList(
        wakeLocks: List<WakeLock>,
        status: Int,
        query: String
    ): List<WakeLock> =
        withContext(Dispatchers.Default) {
            return@withContext wakeLocks
                .flag()
                .status(status)
                .search(query)
        }

    // get all WakeLock flag
    fun List<WakeLock>.flag(): List<WakeLock> {
        return this.map {
            it.flag = SP.getInstance().getFlag(it.wakeLockName)
            it
        }
    }

    //search text filter
    fun List<WakeLock>.search(query: String): List<WakeLock> {
        /*lowerCase and no " " */
        val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
        if (q == "") {
            return this
        }
        return this.filter {
            it.wakeLockName.toLowerCase(Locale.ROOT).contains(q)
                    || it.packageName.toLowerCase(Locale.ROOT).contains(q)
        }
    }

    //status filter
    fun List<WakeLock>.status(status: Int): List<WakeLock> {
        return when (status) {
            4 -> this.sortedByDescending { it.count }
            5 -> this.sortByName()
            6 -> this.sortedByDescending { it.countTime }
            else -> this
        }
    }

    //sort by wakeLockName
    fun List<WakeLock>.sortByName(): List<WakeLock> {
        return this.sortedWith(Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.wakeLockName, s2.wakeLockName)
        })
    }

}