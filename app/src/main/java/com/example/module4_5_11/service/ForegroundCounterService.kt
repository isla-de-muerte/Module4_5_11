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

class ForegroundCounterService : Service() {

    companion object {
        const val ACTION_START = "action_start_counter"
        const val ACTION_STOP = "action_stop_counter"

        const val EXTRA_SECONDS = "extra_seconds"

        const val BROADCAST_ACTION = "counter_service_update"
        const val NOTIFICATION_ID = 1001
    }

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private var timerJob: Job? = null
    private var seconds = 0

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> startCounter()
            ACTION_STOP -> stopSelf()
        }
        return START_STICKY
    }

    private fun startCounter() {
        if (timerJob != null) return

        startForeground(
            NOTIFICATION_ID,
            NotificationHelper.buildNotification(
                this,
                "Счётчик времени",
                "Прошло 0 секунд"
            )
        )

        timerJob = serviceScope.launch {
            while (true) {
                delay(1000)
                seconds++

                val notification = NotificationHelper.buildNotification(
                    this@ForegroundCounterService,
                    "Счётчик времени",
                    "Прошло $seconds секунд"
                )

                val manager =
                    getSystemService(Service.NOTIFICATION_SERVICE) as android.app.NotificationManager
                manager.notify(NOTIFICATION_ID, notification)

                sendBroadcast(
                    Intent(BROADCAST_ACTION).putExtra(EXTRA_SECONDS, seconds)
                )
            }
        }
    }

    override fun onDestroy() {
        timerJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}