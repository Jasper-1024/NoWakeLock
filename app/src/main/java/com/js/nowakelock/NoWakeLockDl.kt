package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.repository.AppInfoRepository
import com.js.nowakelock.data.repository.WakeLockRepository
import com.js.nowakelock.viewmodel.AppListViewModel
import com.js.nowakelock.viewmodel.WakeLockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var noWakeLockModule = module {
    single(named("AR")) {
        AppInfoRepository(
            AppDatabase.getInstance(BasicApp.context).appInfoDao()
        )
    }
    single(named("WLR")) {
        WakeLockRepository(
            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
        )
    }
    viewModel {
        AppListViewModel(get(named("AR")))
    }
    viewModel {
        WakeLockViewModel(get(named("WLR")))
    }
//    single {
//        DataRepository(
//            AppDatabase.getInstance(
//                BasicApp.context
//            )
//        )
//    }
}