package com.js.nowakelock.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
import java.io.File


class MainActivity : AppCompatActivity() {
    //    var dataRepository = inject<DataRepository>()
//    var test1 = inject<AppInfoRepository>()
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //Toolbar
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        //NavigationView
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        //start BackService
//        startBackService()
//        //set NotificationChannel
//        createNotificationChannel()

//        test()
//        fixPermission(this.createDeviceProtectedStorageContext())
        getString(R.string.android)
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }

    //    private fun startBackService() {
//        val service = Intent(this, NWLService::class.java)
//        startService(service)
//    }
//    @SuppressLint("SetWorldReadable")
//    private fun test() {
//        val context1 = this.createDeviceProtectedStorageContext()
////        val dir = context1.dataDir
////        if (dir.exists()) {
////            dir.setExecutable(true, false)
////            dir.setReadable(true, false)
////        }
//        LogUtil.d(
//            "Xposed.NoWakeLock",
//            "${context1.filesDir} ${context1.dataDir} ${context1.cacheDir} ${context1.codeCacheDir}"
//        )
//
//        val sharedPref = context1.getSharedPreferences("test1", Context.MODE_PRIVATE)
//
//        with(sharedPref.edit()) {
//            putString("test", "test")
//            apply()
//        }
//    }

//    @SuppressLint("SetWorldReadable")
//    fun fixPermission(context: Context) {
//        AsyncTask.execute {
//            // data dir
//            val filesFolder: File =
//                context.applicationContext.dataDir
//
//            if (filesFolder.exists()) {
//                filesFolder.setExecutable(true, false)
//                filesFolder.setReadable(true, false)
//                filesFolder.listFiles()?.forEach { it ->
//                    it.setExecutable(true, false)
//                    it.setReadable(true, false)
//                    if (it.isDirectory) {
//                        it.listFiles()?.forEach {
//                            it.setExecutable(true, false)
//                            it.setReadable(true, false)
//                        }
//                    }
//                }
//            }
//
//        }
//
//        val sharedPrefsFolder =
//            File(context.dataDir.absolutePath + "/shared_prefs")
//        if (sharedPrefsFolder.exists()) {
//            sharedPrefsFolder.setExecutable(true, false)
//            sharedPrefsFolder.setReadable(true, false)
//            val f = File(sharedPrefsFolder.absolutePath + "/" + "test1" + ".xml")
//            if (f.exists()) {
//                f.setReadable(true, false)
//            }
//        }
//
//    }
}
