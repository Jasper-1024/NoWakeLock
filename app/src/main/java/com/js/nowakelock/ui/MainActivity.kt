package com.js.nowakelock.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.js.nowakelock.R
import com.js.nowakelock.XposedHook.DataRepository
import com.js.nowakelock.data.repository.AppInfoRepository
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {

//    var dataRepository = inject<DataRepository>()
//    var test1 = inject<AppInfoRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
