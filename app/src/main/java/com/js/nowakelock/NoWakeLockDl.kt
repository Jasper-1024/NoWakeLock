package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.repository.AppInfoRepository
import com.js.nowakelock.data.repository.IAppInfoRepository
import com.js.nowakelock.data.repository.frepository.FRepository
import com.js.nowakelock.data.repository.frepository.IAlarmR
import com.js.nowakelock.data.repository.frepository.IServiceR
import com.js.nowakelock.data.repository.frepository.IWakelockR
import com.js.nowakelock.data.repository.inforepository.IInfoRepository
import com.js.nowakelock.data.repository.inforepository.InfoRepository
import com.js.nowakelock.ui.app.setting.AppSettingViewModel
import com.js.nowakelock.ui.appList.AppListViewModel
import com.js.nowakelock.ui.fragment.fbase.FViewModel
import com.js.nowakelock.ui.help.HelpViewModel
import com.js.nowakelock.ui.infofragment.InfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

var noWakeLockModule = module {

    /** AlarmRepository */
    single<FRepository>(named("AR")) {
        IAlarmR(
            AppDatabase.getInstance(BasicApp.context).alarmDao()
        )
    }

    /** AppInfoRepository */
    single<AppInfoRepository>(named("APR")) {
        IAppInfoRepository(
            AppDatabase.getInstance(BasicApp.context).appInfoDao()
        )
    }

    /** ServiceRepository*/
    single<FRepository>(named("SR")) {
        IServiceR(
            AppDatabase.getInstance(BasicApp.context).serviceDao()
        )
    }

    /** WakeLockRepository */
    single<FRepository>(named("WR")) {
        IWakelockR(
            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
        )
    }

    /** InfoRepository */
    single<InfoRepository>(named("IR")) { (type: String) ->
        IInfoRepository(AppDatabase.getInstance(BasicApp.context).infoDao(), type)
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

    /**info*/
    viewModel(named("VMI")) { (name: String, packageName: String, type: String) ->
        InfoViewModel(
            name,
            packageName,
            get(named("IR")) { parametersOf(type) }
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