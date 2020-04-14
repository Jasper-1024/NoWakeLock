package com.js.nowakelock.ui.wakeLock

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.repository.WakeLockRepository
import com.js.nowakelock.data.sp.SP
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.Collator
import java.util.*
import kotlin.Comparator

class WakeLockViewModel(
    wakeLockRepository: WakeLockRepository
) : ViewModel() {
    val TAG = "WakeLockViewModel"

    var wakeLocks: LiveData<List<WakeLock>> = wakeLockRepository.getWakeLocks()

    //save wakelock flag
    fun setWakeLockFlag(wakeLock: WakeLock) = viewModelScope.launch(Dispatchers.IO) {
        SP.getInstance().setFlag(wakeLock.wakeLockName, wakeLock.flag)
    }

    suspend fun list(wakeLocks: List<WakeLock>, catch: cache): List<WakeLock> =
        withContext(Dispatchers.Default) {
            return@withContext wakeLocks.flag()
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    // get all WakeLock flag
    fun List<WakeLock>.flag(): List<WakeLock> {
        return this.map {
            it.flag = SP.getInstance().getFlag(it.wakeLockName)
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

//    suspend fun WakeLockList(
//        wakeLocks: List<WakeLock>,
//        status: Int,
//        query: String
//    ): List<WakeLock> =
//        withContext(Dispatchers.Default) {
//            return@withContext wakeLocks
//                .flag()
//                .status(status)
//                .search(query)
//        }
//
//    // get all WakeLock flag
//    fun List<WakeLock>.flag(): List<WakeLock> {
//        return this.map {
//            it.flag = SP.getInstance().getFlag(it.wakeLockName)
//            it
//        }
//    }
//
//    //search text filter
//    fun List<WakeLock>.search(query: String): List<WakeLock> {
//        /*lowerCase and no " " */
//        val q = query.toLowerCase(Locale.ROOT).trim { it <= ' ' }
//        if (q == "") {
//            return this
//        }
//        return this.filter {
//            it.wakeLockName.toLowerCase(Locale.ROOT).contains(q)
//                    || it.packageName.toLowerCase(Locale.ROOT).contains(q)
//        }
//    }
//
//    //status filter
//    fun List<WakeLock>.status(status: Int): List<WakeLock> {
//        return when (status) {
//            4 -> this.sortedByDescending { it.count }
//            5 -> this.sortByName()
//            6 -> this.sortedByDescending { it.countTime }
//            else -> this
//        }
//    }
//
//    //sort by wakeLockName
//    fun List<WakeLock>.sortByName(): List<WakeLock> {
//        return this.sortedWith(Comparator { s1, s2 ->
//            Collator.getInstance(Locale.getDefault()).compare(s1.wakeLockName, s2.wakeLockName)
//        })
//    }
}