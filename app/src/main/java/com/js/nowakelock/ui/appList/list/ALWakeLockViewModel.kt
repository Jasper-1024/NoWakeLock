package com.js.nowakelock.ui.appList.list

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

class ALWakeLockViewModel(
    private var packageName: String,
    private var WakeLockRepository: WakeLockRepository
) : ViewModel() {

    val TAG = "ALWakeLockViewModel"

    var wakeLocks: LiveData<List<WakeLock>> = WakeLockRepository.getWakeLocks(packageName)

    fun syncWakeLocks() = viewModelScope.launch {
//        LogUtil.d("test1",packageName)
        WakeLockRepository.syncWakelocks(packageName)
    }

    //save wakelock_st
    fun setWakeLock_st(wakeLock: WakeLock) = viewModelScope.launch(Dispatchers.IO) {
//        SP.getInstance().setFlag(wakeLock.wakeLockName, wakeLock.flag)
        WakeLockRepository.setWakeLock_st(
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
            val tmp = WakeLockRepository.getWakeLock_st(it.wakeLockName)
            it.flag = tmp.flag
            it.allowTimeinterval = tmp.allowTimeinterval
            it
        }
    }

    private fun search(wakeLock: WakeLock) = wakeLock.wakeLockName

    // get sort method
    private fun sort(sort: Int): Comparator<WakeLock> {
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