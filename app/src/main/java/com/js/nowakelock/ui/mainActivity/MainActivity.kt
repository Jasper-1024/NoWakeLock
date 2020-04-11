package com.js.nowakelock.ui.mainActivity

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
import com.js.nowakelock.base.LogUtil


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()

        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        //Toolbar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        //NavigationView
        findViewById<NavigationView>(R.id.nav_view)
            .setupWithNavController(navController)

//        findViewById<NavigationView>(R.id.nav_view)
//            .setNavigationItemSelectedListener(this)
//        visibilityNavElements(navController)
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

    //menu handler
    fun statusUser(menu: MenuItem) {
        viewModel.status.postValue(1)
        menu.isChecked = true
    }

    fun statusSystem(menu: MenuItem) {
        viewModel.status.postValue(2)
        menu.isChecked = true
    }

    fun statusAll(menu: MenuItem) {
        viewModel.status.postValue(3)
        menu.isChecked = true
    }

    fun statusCount(menu: MenuItem) {
        viewModel.status.postValue(4)
        menu.isChecked = true
    }

    fun statusName(menu: MenuItem) {
        viewModel.status.postValue(5)
        menu.isChecked = true
    }

    fun statusCountTime(menu: MenuItem) {
        viewModel.status.postValue(6)
        menu.isChecked = true
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
                viewModel.searchText.postValue(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
//                Log.i("test1", "Search change=$query")
                viewModel.searchText.postValue(query)
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
//                R.id.settingsFragment -> {
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
