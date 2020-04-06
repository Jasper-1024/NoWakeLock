package com.js.nowakelock.ui.mainActivity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

//      var dataRepository = inject<DataRepository>()
//    var test1 = inject<AppInfoRepository>()

    companion object {
        val appListStatus = "appListStatus"
    }


    private lateinit var viewModel: MainViewModel

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

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //get appListStatus
        savedInstanceState?.let {
            viewModel.appListStatus.value = it.getInt(appListStatus)
        }

        //start BackService
//        startBackService()
//        //set NotificationChannel
//        createNotificationChannel()

//        test()
//        fixPermission(this.createDeviceProtectedStorageContext())
//        getString(R.string.android)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(appListStatus, viewModel.appListStatus.value!!)
        super.onSaveInstanceState(outState)
    }

//    override fun onDestroy() {
//        viewModel.appListStatus.value?.let {
//            saveAppListStats(viewModel.appListStatus.value!!)
//            LogUtil.d("test1",viewModel.appListStatus.value.toString())
//        }
//        super.onDestroy()
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }

//    private fun loadAppListStatus(): Int {
//        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
//        LogUtil.d("test1","ss ${sharedPref.getInt("AppListStatus", 1)}")
//        return sharedPref.getInt("AppListStatus", 1)
//    }
//
//    private fun saveAppListStats(appListStatus: Int) {
//        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
//        sharedPref.edit().putInt("AppListStatus", appListStatus).apply()
//    }
//

    //menu handler
    fun statusUser(menu: MenuItem) = viewModel.appListStatus.postValue(1)
    fun statusSystem(menu: MenuItem) = viewModel.appListStatus.postValue(2)
    fun statusCount(menu: MenuItem) = viewModel.appListStatus.postValue(3)
    fun statusAll(menu: MenuItem) = viewModel.appListStatus.postValue(4)

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle item selection
//        return when (item.itemId) {
//            R.id.menu_filter_user -> {
//                LogUtil.d("test1", "4")
//                viewModel.appListStatus.postValue(1)
//                true
//            }
//            R.id.menu_filter_system -> {
//                viewModel.appListStatus.postValue(2)
//                true
//            }
//            R.id.menu_filter_all -> {
//                viewModel.appListStatus.postValue(3)
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

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
