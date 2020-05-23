package com.js.nowakelock.ui.mainActivity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.js.nowakelock.R
import com.js.nowakelock.base.cache


class MainActivity : AppCompatActivity() {

//    companion object{
//        fun isModuleActive():Boolean{
//            return false
//        }
//    }

    private val viewModel: MainViewModel by viewModels {
        MainUtils.ViewModelFactory(this)
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration =
            AppBarConfiguration(
                setOf(
                    R.id.alarmFragment,
                    R.id.serviceFragment,
                    R.id.wakeLockFragment,
                    R.id.appListFragment
                ), drawerLayout
            )

        //Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        //NavigationView
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

        //get cache
        viewModel.status.postValue(loadStatus())

//        visibilityNavElements(navController)
    }

    override fun onDestroy() {
        //save cathe
        viewModel.status.value?.let { saveStatus(it) }
        super.onDestroy()
    }

    //ToolBar menu
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

    private fun saveStatus(cache: cache) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("app", cache.app)
            putInt("sort", cache.sort)
//            putString("query", cache.query)
            apply()
        }
//        LogUtil.d("test1","$cache")
    }

    private fun loadStatus(): cache {
        return cache().apply {
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            sharedPref.apply {
                app = getInt("app", 1)
                sort = getInt("sort", 1)
//                query = getString("query", "") ?: ""
            }
//            LogUtil.d("test12","$this")
        }
    }

//    private fun setToolbarMenu(cache: cache) {
//        when (cache.app) {
//            1 -> toolbar.menu.findItem(R.id.menu_filter_user).isChecked = true
//            2 -> toolbar.menu.findItem(R.id.menu_filter_system).isChecked = true
//        }
//        when (cache.sort) {
//            1 -> setSortCheck(toolbar.menu.findItem(R.id.menu_sort_name))
//            2 -> setSortCheck(toolbar.menu.findItem(R.id.menu_sort_count))
//            3 -> setSortCheck(toolbar.menu.findItem(R.id.menu_sort_countime))
//        }
//    }

    //handler status menu
    fun statusUser(menu: MenuItem) {
        viewModel.postapp(1)
        menu.isChecked = true
    }

    fun statusSystem(menu: MenuItem) {
        viewModel.postapp(2)
        menu.isChecked = true
    }

    fun statusAll(menu: MenuItem) {
        viewModel.postapp(3)
        menu.isChecked = true
    }

    //handler sort menu
    fun sortName(menu: MenuItem) {
        viewModel.postsort(1)
        setSortCheck(menu)
    }

    fun sortCount(menu: MenuItem) {
        viewModel.postsort(2)
        setSortCheck(menu)
    }

    fun sortCountTime(menu: MenuItem) {
        viewModel.postsort(3)
        setSortCheck(menu)
    }

    private fun setSortCheck(item: MenuItem) {
        toolbar.menu.findItem(R.id.menu_sort_name).isChecked = false
        toolbar.menu.findItem(R.id.menu_sort_count).isChecked = false
        toolbar.menu.findItem(R.id.menu_sort_countime).isChecked = false
        item.isChecked = true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Search
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        setSearchView(searchView)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                Log.i("test1", "Search submit=$query")
                viewModel.postquery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
//                Log.i("test1", "Search change=$query")
                viewModel.postquery(query)
                return true
            }
        })
    }


//    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
//        val id = menuItem.itemId
//        LogUtil.d("test1","123")
//        findViewById<DrawerLayout>(R.id.drawer_layout).closeDrawers()
//        when (id) {
//            R.id.settingsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
//        }
//        return true
//    }

//    private fun visibilityNavElements(navController: NavController) {
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.appListFragment -> {
//                    LogUtil.d("test1","222")
//                    val t = toolbar.menu.findItem(R.id.menu_filter)
//                    t.isVisible = false
////                    toolbar.menu.findItem(R.id.menu_filter).isVisible = false
////                    toolbar.menu.findItem(R.id.search).isVisible = false
////                    menuInflater.inflate(R.menu.options_menu, toolbar.menu)
//                }
//                else -> {
////                    toolbar.menu.findItem(R.id.menu_filter).isVisible = true
////                    toolbar.menu.findItem(R.id.search).isVisible = true
////                    menuInflater.inflate(R.menu.options_menu, toolbar.menu)
//                }
//            }
//        }
//    }
}
