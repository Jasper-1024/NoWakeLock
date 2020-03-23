package com.js.nowakelock.ui.databding

import android.R
import android.net.Uri
import android.widget.ImageView
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
