package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.repository.AppInfoRepository
import com.js.nowakelock.data.repository.WakeLockRepository
import com.js.nowakelock.data.repository.mAppInfoRepository
import com.js.nowakelock.data.repository.mWakeLockRepository
import com.js.nowakelock.ui.app.setting.AppSettingViewModel
import com.js.nowakelock.ui.appList.AppListViewModel
import com.js.nowakelock.ui.help.HelpViewModel
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
    /**applist*/
    viewModel {
        AppListViewModel(get(named("AR")))
    }

    /**appsetting*/
    viewModel { (packageName: String) ->
        AppSettingViewModel(packageName, get(named("AR")))
    }

    /**wakelock*/
    viewModel { (packageName: String) ->
        WakeLockViewModel(packageName, get(named("WLR")))
    }
//    viewModel { (packageName: String) ->
//        ALWakeLockViewModel(
//            packageName,
//            get(named("WLR"))
//        )
//    }

    /**help*/
    viewModel {
        HelpViewModel()
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