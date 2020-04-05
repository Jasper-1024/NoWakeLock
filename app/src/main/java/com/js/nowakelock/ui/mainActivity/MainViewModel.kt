package com.js.nowakelock.ui.mainActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val TAG = "MainViewModel"
    val appListStatus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(1)
    }
}

