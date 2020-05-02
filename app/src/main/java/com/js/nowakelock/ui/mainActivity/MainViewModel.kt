package com.js.nowakelock.ui.mainActivity

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.js.nowakelock.base.cache

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {
    val TAG = "MainViewModel"

    val status: MutableLiveData<cache> by lazy {
        MutableLiveData<cache>(cache())
    }

    fun postapp(app: Int) {
        status.postValue(cache(app, status.value!!.sort, status.value!!.query))
//        state.set("app",app)
    }

    fun postsort(sort: Int) {
        status.postValue(cache(status.value!!.app, sort, status.value!!.query))
//        state.set("sort",sort)
    }

    fun postquery(query: String) {
        status.postValue(cache(status.value!!.app, status.value!!.sort, query))
//        state.set("query",query)
    }
}



