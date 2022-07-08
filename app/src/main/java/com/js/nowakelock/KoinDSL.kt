package com.js.nowakelock

import com.js.nowakelock.data.db.AppDatabase
import com.js.nowakelock.data.db.Type
import com.js.nowakelock.data.repository.appda.AppDaAR
import com.js.nowakelock.data.repository.appda.AppDaRepo
import com.js.nowakelock.data.repository.das.FR
import com.js.nowakelock.data.repository.das.IAlarmR
import com.js.nowakelock.data.repository.das.IServiceR
import com.js.nowakelock.data.repository.das.IWakelockR
import com.js.nowakelock.ui.appDa.AppDaViewModel
import com.js.nowakelock.ui.fragment.fbase.FBaseViewModel
import com.js.nowakelock.ui.mainActivity.MainViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

var repository = module {

    /** AppRepo */
    single<AppDaRepo>(named("AppDaR")) {
        AppDaAR(
            AppDatabase.getInstance(BasicApp.context).appInfoDao(),
            AppDatabase.getInstance(BasicApp.context).dADao(),
        )
    }
    single<FR>(named("WakelockR")) {
        IWakelockR(AppDatabase.getInstance(BasicApp.context).dADao())
    }

    single<FR>(named("AlarmR")) {
        IAlarmR(AppDatabase.getInstance(BasicApp.context).dADao())
    }

    single<FR>(named("ServiceR")) {
        IServiceR(AppDatabase.getInstance(BasicApp.context).dADao())
    }
}

var viewModel = module {

    viewModel(named("FVm")) { (packageName: String, type: Type) ->
        FBaseViewModel(
            packageName,
            when (type) {
                Type.Wakelock -> get(named("WakelockR"))
                Type.Alarm -> get(named("AlarmR"))
                Type.Service -> get(named("ServiceR"))
                else -> get(named("WakelockR"))
            }
        )
    }

    // MainViewModel
    viewModel(named("MainVm")) {
        MainViewModel()
    }

    viewModel(named("AppDaVM")) {
        AppDaViewModel(get(named("AppDaR")))
    }

}