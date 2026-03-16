package com.example.module4_5_11.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BackgroundTimerService : Service() {

    companion object {
        const val EXTRA_DURATION = "extra_duration"
        const val NOTIFICATION_ID = 1002
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private var timerJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val seconds = intent?.getIntExtra(EXTRA_DURATION, 0) ?: 0
        if (seconds > 0) {
            timerJob?.cancel()
            timerJob = serviceScope.launch {
                delay(seconds * 1000L)
                NotificationHelper.showSimpleNotification(
                    this@BackgroundTimerService,
                    NOTIFICATION_ID,
                    "Таймер завершён!",
                    "Время вышло"
                )
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timerJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}