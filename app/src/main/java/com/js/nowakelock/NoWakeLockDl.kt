package com.js.nowakelock

import com.js.nowakelock.data.repository.FRepository
import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.repository.*
import com.js.nowakelock.ui.app.setting.AppSettingViewModel
import com.js.nowakelock.ui.appList.AppListViewModel
import com.js.nowakelock.ui.help.HelpViewModel
import com.js.nowakelock.ui.fragment.fbase.FViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

var noWakeLockModule = module {

    /** AlarmRepository */
    single<FRepository>(named("AR")) {
        mAlarmR(
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
    single<FRepository>(named("SR")) {
        mServiceR(
            AppDatabase.getInstance(BasicApp.context).serviceDao()
        )
    }

    /** WakeLockRepository */
    single<FRepository>(named("WR")) {
        mWakelockR(
            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
        )
    }


    /**alarm*/
    viewModel(named("VMA")) { (packageName: String) ->
        FViewModel(
            packageName,
            get(named("AR"))
        )
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
    viewModel(named("VMS")) { (packageName: String) ->
        FViewModel(
            packageName,
            get(named("SR"))
        )
    }

    /**wakelock*/
    viewModel(named("VMW")) { (packageName: String) ->
        FViewModel(
            packageName,
            get(named("WR"))
        )
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