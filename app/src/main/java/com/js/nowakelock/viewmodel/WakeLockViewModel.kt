package com.js.nowakelock.viewmodel

import androidx.lifecycle.ViewModel
import com.js.nowakelock.data.repository.WakeLockRepository

class WakeLockViewModel(private var wakeLockRepository: WakeLockRepository) : ViewModel()