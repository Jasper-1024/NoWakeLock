package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.repository.*
import com.js.nowakelock.ui.alarm.AlarmViewModel
import com.js.nowakelock.ui.app.setting.AppSettingViewModel
import com.js.nowakelock.ui.appList.AppListViewModel
import com.js.nowakelock.ui.help.HelpViewModel
import com.js.nowakelock.ui.service.ServiceViewModel
import com.js.nowakelock.ui.wakeLock.WakeLockViewModel
import com.js.nowakelock.data.repository.mAlarmRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var noWakeLockModule = module {

    /** AlarmRepository */
    single<AlarmRepository>(named("AR")) {
        mAlarmRepository(
            AppDatabase.getInstance(BasicApp.context).alarmDao()
        )
    }

    /** AppInfoRepository */
    single<AppInfoRepository>(named("APR")) {
        mAppInfoRepository(
            AppDatabase.getInstance(BasicApp.context).appInfoDao()
        )
    }

    /** ServiceRepository*/
    single<ServiceRepository>(named("SR")) {
        mServiceRepository(
            AppDatabase.getInstance(BasicApp.context).serviceDao()
        )
    }

    /** WakeLockRepository */
    single<WakeLockRepository>(named("WLR")) {
        mWakeLockRepository(
            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
        )
    }

    /**alarm*/
    viewModel { (packageName: String) ->
        AlarmViewModel(packageName, get(named("AR")))
    }

    /**applist*/
    viewModel {
        AppListViewModel(get(named("APR")))
    }

    /**appsetting*/
    viewModel { (packageName: String) ->
        AppSettingViewModel(packageName, get(named("APR")))
    }

    /**service*/
    viewModel { (packageName: String) ->
        ServiceViewModel(packageName, get(named("SR")))
    }

    /**wakelock*/
    viewModel { (packageName: String) ->
        WakeLockViewModel(packageName, get(named("WLR")))
    }

    /**help*/
    viewModel {
        HelpViewModel()
    }
//    viewModel { (packageName: String) ->
//        ALWakeLockViewModel(
//            packageName,
//            get(named("WLR"))
//        )
//    }

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