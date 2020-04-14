package com.js.nowakelock

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.js.nowakelock.ui.settings.ThemeHelper
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

        // set Theme
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val themePref =
            sharedPreferences.getString("theme_list", ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(themePref!!)
    }

//    override fun onTerminate(){
//        super.onTerminate()
//        LogUtil.d("test1","onTerminate")
//    }
}