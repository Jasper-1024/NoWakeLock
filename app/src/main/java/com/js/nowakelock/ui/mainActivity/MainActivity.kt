package com.js.nowakelock.ui.mainActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.js.nowakelock.R
import com.js.nowakelock.base.LogUtil
import com.js.nowakelock.base.Status
import com.js.nowakelock.ui.base.AppType
import com.js.nowakelock.ui.base.Sort
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named


class MainActivity : AppCompatActivity() {

    //check module active
    private fun isModuleActive(): Boolean {
        return false
    }

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout

    private val mainViewModel: MainViewModel by viewModel(named("MainVm"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.wakeLockFragment), drawerLayout)

        // 初始化
        drawerLayout = findViewById(R.id.drawer_layout)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)// toolbar 替换 ActionBar
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark)//设置侧边栏颜色

        /* 设置导航组件,一定要在 setSupportActionBar 之后 */
        toolbar.setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)//设置导航组件

        //检查模块是否激活
        if (!isModuleActive()) {
            Toast.makeText(this, getString(R.string.active), Toast.LENGTH_LONG).show()
        }
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.drawer_layout))
            .apply {
                setStatusBarBackground(R.color.colorPrimaryDark)
            }
    }

    //ToolBar menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    fun statusUser(menu: MenuItem) {
        mainViewModel.type.postValue(AppType.User)
        menu.isChecked = true
    }

    fun statusSystem(menu: MenuItem) {
        mainViewModel.type.postValue(AppType.System)
        menu.isChecked = true
    }

    fun statusAll(menu: MenuItem) {
        mainViewModel.type.postValue(AppType.All)
        menu.isChecked = true
    }

    fun sortName(menu: MenuItem) {
        mainViewModel.sort.postValue(Sort.Name)
        setSortCheck(menu)
    }

    fun sortCount(menu: MenuItem) {
        mainViewModel.sort.postValue(Sort.Count)
        setSortCheck(menu)
    }

    fun sortCountTime(menu: MenuItem) {
        mainViewModel.sort.postValue(Sort.CountTime)
        setSortCheck(menu)
    }

    private fun setSortCheck(item: MenuItem) {
        toolbar.menu.findItem(R.id.menu_sort_name).isChecked = false
        toolbar.menu.findItem(R.id.menu_sort_count).isChecked = false
        toolbar.menu.findItem(R.id.menu_sort_countime).isChecked = false
        item.isChecked = true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // 搜索栏
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        setSearchView(searchView)
        return super.onPrepareOptionsMenu(menu)
    }

    private fun setSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.query.postValue(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                mainViewModel.query.postValue(query)
                return true
            }
        })
    }
}
