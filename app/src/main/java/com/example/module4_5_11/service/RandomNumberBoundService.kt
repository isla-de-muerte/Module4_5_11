package com.example.module4_5_11.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class RandomNumberBoundService : Service() {

    inner class LocalBinder : Binder() {
        fun getService(): RandomNumberBoundService = this@RandomNumberBoundService
    }

    private val binder = LocalBinder()
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private var randomJob: Job? = null

    var currentNumber: Int = 0
        private set

    override fun onBind(intent: Intent?): IBinder {
        startGenerating()
        return binder
    }

    private fun startGenerating() {
        if (randomJob != null) return

        randomJob = serviceScope.launch {
            while (true) {
                delay(1000)
                currentNumber = Random.nextInt(0, 101)
            }
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        randomJob?.cancel()
        randomJob = null
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        randomJob?.cancel()
        serviceScope.cancel()
        super.onDestroy()
    }
}