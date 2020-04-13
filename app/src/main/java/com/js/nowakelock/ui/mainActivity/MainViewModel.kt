package com.js.nowakelock.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.js.nowakelock.base.LogUtil

class MainViewModel(private val state: SavedStateHandle) : ViewModel() {
    val TAG = "MainViewModel"

    val status: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(5)
    }

    val searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
}

