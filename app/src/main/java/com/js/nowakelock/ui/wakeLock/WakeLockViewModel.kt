package com.js.nowakelock.ui.wakeLock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.repository.WakeLockRepository
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

//    fun getwakelocks(packageName: String) = WakeLockRepository.getWakeLocks(packageName)

    fun syncWakeLocks() = viewModelScope.launch {
//        LogUtil.d("test1",packageName)
        WakeLockRepository.syncWakelocks(packageName)
    }

    fun setWakeLockFlag(wakeLock: WakeLock) = viewModelScope.launch {
        WakeLockRepository.setWakeLockFlag(wakeLock)
    }

    suspend fun WakeLockList(appInfos: List<WakeLock>, status: Int, query: String): List<WakeLock> =
        withContext(Dispatchers.Default) {
            return@withContext appInfos
                .status(status)
                .search(query)
        }


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

    fun List<WakeLock>.status(status: Int): List<WakeLock> {
        return when (status) {
            4 -> this.sortedByDescending { it.count }
            5 -> this.sortByName()
            else -> this
        }
    }

    fun List<WakeLock>.sortByName(): List<WakeLock> {
        return this.sortedWith(Comparator { s1, s2 ->
            Collator.getInstance(Locale.getDefault()).compare(s1.wakeLockName, s2.wakeLockName)
        })
    }

}