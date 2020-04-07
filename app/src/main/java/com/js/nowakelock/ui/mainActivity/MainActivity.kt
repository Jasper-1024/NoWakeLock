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


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
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
    }


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
    fun statusUser(menu: MenuItem) = viewModel.appListStatus.postValue(1)
    fun statusSystem(menu: MenuItem) = viewModel.appListStatus.postValue(2)
    fun statusCount(menu: MenuItem) = viewModel.appListStatus.postValue(3)
    fun statusAll(menu: MenuItem) = viewModel.appListStatus.postValue(4)

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
}
