package com.js.nowakelock

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
import com.js.nowakelock.base.Status
import com.js.nowakelock.base.cache


class MainActivity : AppCompatActivity() {

    //check module active
    private fun isModuleActive(): Boolean {
        return false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check module active
        if (!isModuleActive()) {
            Toast.makeText(this, getString(R.string.active), Toast.LENGTH_LONG).show()
        }
    }
}
