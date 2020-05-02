package com.js.nowakelock.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.js.nowakelock.base.cache

class MainViewModel : ViewModel() {
    val TAG = "MainViewModel"

    val status: MutableLiveData<cache> by lazy {
        MutableLiveData<cache>(cache())
    }

    fun postapp(app: Int) {
        status.postValue(cache(app, status.value!!.sort, status.value!!.query))
    }

    fun postsort(sort: Int) {
        status.postValue(cache(status.value!!.app, sort, status.value!!.query))
    }

    fun postquery(query: String) {
        status.postValue(cache(status.value!!.app, status.value!!.sort, query))
    }

//    val app: MutableLiveData<Int> by lazy {
//        MutableLiveData<Int>(1)
//    }
//
//    val sort: MutableLiveData<Int> by lazy {
//        MutableLiveData<Int>(1)
//    }
//
//    val searchText: MutableLiveData<String> by lazy {
//        MutableLiveData<String>("")
//    }
}

