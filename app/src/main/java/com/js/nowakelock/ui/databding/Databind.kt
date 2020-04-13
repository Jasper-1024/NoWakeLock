package com.js.nowakelock.ui.databding

import android.R
import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.js.nowakelock.BasicApp
import com.js.nowakelock.base.TimeUtil
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.WakeLock

@BindingAdapter("loadIcon")
fun LoadIcon(imageView: ImageView, appInfo: AppInfo) {
    val options = RequestOptions()
        .error(R.drawable.sym_def_app_icon)
        .placeholder(R.drawable.sym_def_app_icon)
    val uri = Uri.parse("android.resource://" + appInfo.packageName + "/" + appInfo.icon)
    Glide.with(imageView.context)
        .applyDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_RGB_565))
        .load(uri)
        .apply(options)
        .into(imageView)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("loadAppInfoCount")
fun loadAppInfoCount(textView: TextView, appInfo: AppInfo) {
    val (_, _, _, _, _, _, _, _, _, count, blockCount) = appInfo //😂
    textView.text = "Count: $count BlockCount: $blockCount"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("loadWakeLockCount")
fun loadWakeLockCount(textView: TextView, wakeLock: WakeLock) {
    val (_, _, _, count, blockCount) = wakeLock
    textView.text =
        "${BasicApp.context.getString(com.js.nowakelock.R.string.Count)}: $count " +
                "${BasicApp.context.getString(com.js.nowakelock.R.string.BlockCount)}: $blockCount"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("loadWakeLockCountTime")
fun loadWakeLockCountTime(textView: TextView, wakeLock: WakeLock) {
    val (_, _, _, _, _, countTime, blockCountTime) = wakeLock
    textView.text =
        "${BasicApp.context.getString(com.js.nowakelock.R.string.CountTime)}: ${TimeUtil.getTime(
            countTime
        )} " +
                "${BasicApp.context.getString(com.js.nowakelock.R.string.BlockCountTime)}: ${TimeUtil.getTime(
                    blockCountTime
                )}"
}

@BindingAdapter("test")
fun test(textView: TextView, tmp: Int) {
    textView.text = tmp.toString()
}



