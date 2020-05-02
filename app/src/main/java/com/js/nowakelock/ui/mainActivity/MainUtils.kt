package com.js.nowakelock.ui.mainActivity

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

object MainUtils {
    fun ViewModelFactory(activity: MainActivity): MainViewModelFactory {
        return MainViewModelFactory(activity)
    }
}

class MainViewModelFactory(
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST") //忽略警告
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,//传入实例化类型
        handle: SavedStateHandle
    ): T {
        return MainViewModel(handle) as T
    }
}