package com.js.nowakelock.ui.databinding

import android.R
import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseMethod
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.js.nowakelock.base.getFormattedTime
import com.js.nowakelock.data.db.entity.AppInfo
import com.js.nowakelock.data.db.entity.DA
import com.js.nowakelock.ui.databinding.item.BaseItem
import java.util.concurrent.TimeUnit


object DataBind {
    @JvmStatic
    @BindingAdapter("loadIcon")
    fun loadIcon(imageView: ImageView, appInfo: AppInfo) {
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

    @JvmStatic
    @BindingAdapter("items")
    fun setRecyclerViewItems(
        recyclerView: RecyclerView,
        items: List<BaseItem>?
    ) {
        var adapter = (recyclerView.adapter as? RecycleAdapter)
        if (adapter == null) {
            adapter = RecycleAdapter()
            recyclerView.adapter = adapter
        }
        // items 为空 orEmpty 返回空实例
        adapter.submitList(items.orEmpty())
    }

    @JvmStatic
    @SuppressLint("SetTextI18n")
    @BindingAdapter("showUser")
    fun loadUser(textView: TextView, userId: Int) {
        textView.text = "$userId"
    }

    @JvmStatic
    @SuppressLint("SetTextI18n")
    @BindingAdapter("showCount")
    fun loadCount(textView: TextView, count: Int) {
        textView.text = "$count"
    }

    @JvmStatic
    @SuppressLint("SetTextI18n")
    @BindingAdapter("showTime")
    fun loadTime(textView: TextView, time: Long) {
        var blockedTime = time

        val days: Long = TimeUnit.MILLISECONDS.toDays(blockedTime)
        blockedTime -= TimeUnit.DAYS.toMillis(days)
        val hours: Long = TimeUnit.MILLISECONDS.toHours(blockedTime)
        blockedTime -= TimeUnit.HOURS.toMillis(hours)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(blockedTime)
        blockedTime -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(blockedTime)

        textView.text = getFormattedTime(days, hours, minutes, seconds)
    }
}

object Converter {

    @JvmStatic
    @InverseMethod("stringToLong")
    fun longToString(value: Long): String {
        return if (value == 0.toLong()) {
            ""
        } else {
            value.toString()
        }
    }

    @JvmStatic
    fun stringToLong(value: String): Long {
        // Converts String to long.
        return if (value == "") {
            0
        } else {
            value.toLong()
        }
    }

    @InverseMethod("stringToSet")
    @JvmStatic
    fun setToString(values: Set<String>?): String {
        return if (values == null || values.isEmpty()) {
            ""
        } else {
            var tmp = ""
            values.forEach {
                tmp += "$it\n"
            }
            tmp = tmp.substring(0, tmp.length - 1)
            tmp
        }
    }

    @JvmStatic
    fun stringToSet(value: String?): Set<String> {
        return if (value == null || value == "" || value == "\n") {
            mutableSetOf()
        } else {
            value.split("\n")
                .filter { it.matches(Regex("[^\n ]+")) }
                .toSet()
//            value.split('\n').toSet()
        }
    }


}






