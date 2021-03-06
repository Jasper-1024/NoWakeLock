package com.js.nowakelock.ui.mainActivity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.js.nowakelock.base.Status
import com.js.nowakelock.base.cache


class MainActivity : AppCompatActivity() {

    //check module active
    private fun isModuleActive(): Boolean {
        return false
    }

    private val viewModel by viewModels<MainViewModel>()

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
        //check module active
        if (!isModuleActive()) {
            Toast.makeText(this, getString(R.string.active), Toast.LENGTH_LONG).show()
        }
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
    }

    private fun loadStatus(): cache {
        return cache().apply {
            val sharedPref = getPreferences(Context.MODE_PRIVATE)
            sharedPref.apply {
                app = getInt("app", Status.userApp)
                sort = getInt("sort", Status.sortByName)
//                query = getString("query", "") ?: ""
            }
        }
    }

    //handler status menu
    fun statusUser(menu: MenuItem) {
        viewModel.postapp(Status.userApp)
        menu.isChecked = true
    }

    fun statusSystem(menu: MenuItem) {
        viewModel.postapp(Status.systemApp)
        menu.isChecked = true
    }

    fun statusModified(menu: MenuItem) {
        viewModel.postapp(Status.modifiedApp)
        menu.isChecked = true
    }

    fun statusAll(menu: MenuItem) {
        viewModel.postapp(Status.allApp)
        menu.isChecked = true
    }

    //handler sort menu
    fun sortName(menu: MenuItem) {
        viewModel.postsort(Status.sortByName)
        setSortCheck(menu)
    }

    fun sortCount(menu: MenuItem) {
        viewModel.postsort(Status.sortByCount)
        setSortCheck(menu)
    }

    fun sortCountTime(menu: MenuItem) {
        viewModel.postsort(Status.sortByCountTime)
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
                viewModel.postquery(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
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
