package com.js.nowakelock

import com.js.nowakelock.db.AppDatabase
import org.koin.dsl.module

var noWakeLockModule = module {
    single { DataRepository(AppDatabase.getInstance(BasicApp.context)) }
}