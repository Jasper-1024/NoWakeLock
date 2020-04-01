package com.js.nowakelock.service

import android.app.*
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.js.nowakelock.R
import com.js.nowakelock.ui.MainActivity
import org.koin.android.ext.android.inject

class NWLService : Service() {

    val serviceModel: ServiceModel by inject<ServiceModel>()
    private val binder = LocalBinder()

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): NWLService = this@NWLService
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        bindNotification()
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
                .setPriority(NotificationCompat.PRIORITY_MIN)
                .setContentIntent(pendingIntent)
                .build()

        startForeground(1, notification)
    }

    //for android version 8 and 8.1
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_MIN
            val channel =
                NotificationChannel(getString(R.string.channel_id), name, importance).apply {
                    description = descriptionText
                }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
