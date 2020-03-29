package com.js.nowakelock.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.PRIORITY_LOW
import androidx.core.app.NotificationCompat.PRIORITY_MIN
import com.js.nowakelock.R
import com.js.nowakelock.ui.MainActivity

class Service : Service() {

    override fun onCreate() {
        super.onCreate()
        bindNotification()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    private fun bindNotification() {
        // back to MainActivity
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent, 0)
            }

        val notification: Notification =
            NotificationCompat.Builder(this, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_notification_24dp)
                .setContentTitle(getString(R.string.notofication_title))
                .setContentText(getString(R.string.notofication_text))
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notofication_text))
                )
                .setPriority(PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)
    }
}
