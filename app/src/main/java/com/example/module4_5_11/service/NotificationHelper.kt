package com.example.module4_5_11.service


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.module4_5_11.R

object NotificationHelper {
    const val CHANNEL_ID = "services_channel"
    private const val CHANNEL_NAME = "Services"

    fun createChannel(context: Context) {
        val manager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )

        manager.createNotificationChannel(channel)
    }

    fun buildNotification(
        context: Context,
        title: String,
        text: String
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(text)
            .setOnlyAlertOnce(true)
            .build()
    }

    fun showSimpleNotification(
        context: Context,
        id: Int,
        title: String,
        text: String
    ) {
        val manager =
            context.getSystemService(Service.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(id, buildNotification(context, title, text))
    }
}