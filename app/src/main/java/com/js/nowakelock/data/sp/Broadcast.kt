package com.js.nowakelock.data.sp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Broadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_LOCKED_BOOT_COMPLETED == intent.action) {
            SP.fixPermissionsAsync(context)
        }
    }
}
