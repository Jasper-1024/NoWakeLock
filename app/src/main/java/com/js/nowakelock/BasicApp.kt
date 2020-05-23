package com.js.nowakelock

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.preference.PreferenceManager
import com.js.nowakelock.data.PowerConnectionReceiver
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
            androidContext(this@BasicApp)
            modules(noWakeLockModule)
        }

        // set Theme
        val sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(this)
        val themePref =
            sharedPreferences.getString("theme_list", ThemeHelper.DEFAULT_MODE)
        ThemeHelper.applyTheme(themePref!!)

        // for PowerConnectionReceiver
        registerPowerConnectionReceiver()
    }

    private fun registerPowerConnectionReceiver() {
        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(PowerConnectionReceiver(), filter)
    }
}