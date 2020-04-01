package com.js.nowakelock

import android.app.Application
import android.content.Context
import com.js.nowakelock.base.LogUtil
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BasicApp : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        startKoin {
//            androidLogger()
//            androidFileProperties()
            androidContext(this@BasicApp)
            modules(noWakeLockModule)
        }
    }

//    override fun onTerminate(){
//        super.onTerminate()
//        LogUtil.d("test1","onTerminate")
//    }
}