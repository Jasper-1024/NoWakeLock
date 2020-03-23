package com.js.nowakelock.ui.databding

import android.R
import android.annotation.SuppressLint
import android.net.Uri
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.js.nowakelock.data.db.entity.AppInfo

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
@BindingAdapter("loadCount")
fun loadCount(textView: TextView, appInfo: AppInfo) {
    textView.text = appInfo.count.toString() + "  " + appInfo.blockCount.toString()
}
