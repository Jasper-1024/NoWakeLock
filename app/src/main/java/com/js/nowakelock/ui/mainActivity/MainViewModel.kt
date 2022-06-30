package com.js.nowakelock.ui.mainActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.js.nowakelock.ui.base.AppType
import com.js.nowakelock.ui.base.Sort

class MainViewModel : ViewModel() {
    // toolbar app type
    val type: MutableLiveData<AppType> by lazy {
        MutableLiveData<AppType>(AppType.User)
    }

    // toolbar sort by x
    val sort: MutableLiveData<Sort> by lazy {
        MutableLiveData<Sort>(Sort.Name)
    }

    // search text
    val query: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
}