package com.js.nowakelock.ui.wakeLock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.db.entity.WakeLock_st
import com.js.nowakelock.data.repository.WakeLockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

class WakeLockViewModel(
    private var packageName: String = "",
    var wakeLockRepository: WakeLockRepository
) : ViewModel() {
    val TAG = "WakeLockViewModel"

    var wakeLocks: LiveData<List<WakeLock>> = getwakelocks(packageName)

    private fun getwakelocks(packageName: String): LiveData<List<WakeLock>> {
        return if (packageName == "") {
            wakeLockRepository.getWakeLocks()
        } else {
            wakeLockRepository.getWakeLocks(packageName)
        }
    }

    //save wakelock flag
    fun setWakeLockFlag(wakeLock: WakeLock) = viewModelScope.launch(Dispatchers.IO) {
        wakeLockRepository.setWakeLock_st(
            WakeLock_st(
                wakeLock.wakeLockName,
                wakeLock.flag,
                wakeLock.allowTimeinterval
            )
        )
    }

    suspend fun list(wakeLocks: List<WakeLock>, catch: cache): List<WakeLock> =
        withContext(Dispatchers.Default) {
            return@withContext wakeLocks.flag()
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    // get all WakeLock flag
    suspend fun List<WakeLock>.flag(): List<WakeLock> {
        return this.map {
            val tmp = wakeLockRepository.getWakeLock_st(it.wakeLockName)
            it.flag = tmp.flag
            it.allowTimeinterval = tmp.allowTimeinterval
            it
        }
    }

    fun search(wakeLock: WakeLock) = wakeLock.wakeLockName

    // get sort method
    fun sort(sort: Int): java.util.Comparator<WakeLock> {
        return when (sort) {
            1 -> Comparator<WakeLock> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.wakeLockName, s2.wakeLockName)
            }
            2 -> compareByDescending<WakeLock> { it.count }
            3 -> compareByDescending<WakeLock> { it.countTime }
            else -> Comparator<WakeLock> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.wakeLockName, s2.wakeLockName)
            }
        }
    }
}