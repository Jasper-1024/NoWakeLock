package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.XposedHook.DataRepository
import org.koin.dsl.module

var noWakeLockModule = module {
    single {
        DataRepository(
            AppDatabase.getInstance(
                BasicApp.context
            )
        )
    }
}