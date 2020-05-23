package com.js.nowakelock.ui.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.js.nowakelock.base.cache
import com.js.nowakelock.base.search
import com.js.nowakelock.base.sort
import com.js.nowakelock.data.db.entity.Service
import com.js.nowakelock.data.db.entity.Service_st
import com.js.nowakelock.data.repository.ServiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.text.Collator
import kotlin.Comparator

class ServiceViewModel(
    packageName: String = "",
    var repository: ServiceRepository
) : ViewModel() {
    var list: LiveData<List<Service>> =
        if (packageName == "") repository.getServices()
        else repository.getServices(packageName)

    //save service flag
    fun setFlag(service: Service) = viewModelScope.launch(Dispatchers.IO) {
        repository.setService_st(
            Service_st(service.serviceName, service.flag.get(), service.allowTimeinterval)
        )
    }

    suspend fun list(services: List<Service>, catch: cache): List<Service> =
        withContext(Dispatchers.Default) {
            return@withContext services.flag()
                .search(catch.query, ::search)
                .sort(sort(catch.sort))
        }

    // get all Service flag
    suspend fun List<Service>.flag(): List<Service> {
        return this.map {
            val tmp = repository.getService_st(it.serviceName)
            it.flag.set(tmp.flag)
            it.allowTimeinterval = tmp.allowTimeinterval
            it
        }
    }

    fun search(service: Service) = service.serviceName

    // get sort method
    fun sort(sort: Int): java.util.Comparator<Service> {
        return when (sort) {
            1 -> Comparator<Service> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.serviceName, s2.serviceName)
            }
            2 -> compareByDescending<Service> { it.count }
            3 -> compareByDescending<Service> { it.countTime }
            else -> Comparator<Service> { s1, s2 ->
                Collator.getInstance(Locale.getDefault()).compare(s1.serviceName, s2.serviceName)
            }
        }
    }
}