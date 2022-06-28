package com.js.nowakelock


import org.koin.dsl.module

var noWakeLockModule = module {

//    /** AlarmRepository */
//    single<FRepository>(named("AR")) {
//        IAlarmR(
//            AppDatabase.getInstance(BasicApp.context).alarmDao()
//        )
//    }
//
//    /** AppInfoRepository */
//    single<AppInfoRepository>(named("APR")) {
//        IAppInfoRepository(
//            AppDatabase.getInstance(BasicApp.context).appInfoDao(),
//            AppDatabase.getInstance(BasicApp.context).backupDao()
//        )
//    }
//
//    /** ServiceRepository*/
//    single<FRepository>(named("SR")) {
//        IServiceR(
//            AppDatabase.getInstance(BasicApp.context).serviceDao()
//        )
//    }
//
//    /** WakeLockRepository */
//    single<FRepository>(named("WR")) {
//        IWakelockR(
//            AppDatabase.getInstance(BasicApp.context).wakeLockDao()
//        )
//    }
//
//    single<InfoRepository>(named("IRA")) {
//        IAlarmIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }
//
//    single<InfoRepository>(named("IRS")) {
//        IServiceIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }
//
//    single<InfoRepository>(named("IRW")) {
//        IWakelockIR(AppDatabase.getInstance(BasicApp.context).infoDao())
//    }
//
//    /** BackupRepository */
//    single<BackupRepository>(named("BackupR")) {
//        BackupRepository(
//            AppDatabase.getInstance(BasicApp.context).backupDao()
//        )
//    }
//
//
//    /**alarm*/
//    viewModel(named("VMA")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("AR"))
//        )
//    }
//
//    /**applist*/
//    viewModel {
//        AppListViewModel(get(named("APR")))
//    }
//
//    /**appsetting*/
//    viewModel { (packageName: String) ->
//        AppSettingViewModel(packageName, get(named("APR")))
//    }
//
//    /**service*/
//    viewModel(named("VMS")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("SR"))
//        )
//    }
//
//    /**info*/
//    viewModel(named("VMI")) { (name: String, packageName: String, type: String) ->
//        InfoViewModel(
//            name,
//            packageName,
//            when (type) {
//                "alarm" -> {
//                    get(named("IRA"))
//                }
//                "service" -> {
//                    get(named("IRS"))
//                }
//                "wakelock" -> {
//                    get(named("IRW"))
//                }
//                else -> {
//                    get(named("IRW"))
//                }
//            }
//        )
//    }
//
//    /**wakelock*/
//    viewModel(named("VMW")) { (packageName: String) ->
//        FViewModel(
//            packageName,
//            get(named("WR"))
//        )
//    }
//
//    /**help*/
//    viewModel {
//        HelpViewModel()
//    }
//
//    /**help*/
//    viewModel {
//        SettingsViewModel(get(named("BackupR")))
//    }
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

//    /** InfoRepository */
//    single<InfoRepository>(named("IR")) { (type: String) ->
//        IInfoRepository(AppDatabase.getInstance(BasicApp.context).infoDao(), type)
//    }
//    /**info*/
//    viewModel(named("VMI")) { (name: String, packageName: String, type: String) ->
//        InfoViewModel(
//            name,
//            packageName,
//            get(named("IR")) { parametersOf(type) }
//        )
//    }
}