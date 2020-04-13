package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.entity.WakeLock
import com.js.nowakelock.data.repository.AppInfoRepository
import com.js.nowakelock.data.repository.WakeLockRepository
import com.js.nowakelock.data.repository.mAppInfoRepository
import com.js.nowakelock.data.repository.mWakeLockRepository
import com.js.nowakelock.ui.appList.AppListViewModel
import com.js.nowakelock.ui.appList.list.ALWakeLockViewModel
import com.js.nowakelock.ui.wakeLock.WakeLockViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var noWakeLockModule = module {
    single<AppInfoRepository>(named("AR")) {
        mAppInfoRepository(
            AppDatabase.getInstance(BasicApp.context).appInfoDao()
        )
    }
    single<WakeLockRepository>(named("WLR")) {
        mWakeLockRepository(
            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
        )
    }

    viewModel {
        AppListViewModel(get(named("AR")))
    }
    viewModel { (packageName: String) ->
        ALWakeLockViewModel(
            packageName,
            get(named("WLR"))
        )
    }

    viewModel {
        WakeLockViewModel(get(named("WLR")))
    }
//    viewModel { (packageName: String) ->
//        ALWakeLockViewModel(get(named("WLR")), packageName)
//    }
//    single {
//        DataRepository(
//            AppDatabase.getInstance(
//                BasicApp.context
//            )
//        )
//    }
}