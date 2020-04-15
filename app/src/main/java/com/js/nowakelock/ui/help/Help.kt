package com.js.nowakelock.ui.help

import android.text.Html
import android.text.Spanned
import com.js.nowakelock.BasicApp
import com.js.nowakelock.BuildConfig
import com.js.nowakelock.R

data class Help(
    var app_name: String = BasicApp.context.getString(R.string.app_name),
    var app_version: String = BuildConfig.VERSION_NAME,
    var instructions: Spanned = Html.fromHtml(BasicApp.context.getString(R.string.help_instructions)),
    var license: String = ""
)