package com.example.module4_5_11.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.module4_5_11.service.ForegroundCounterService

@Composable
fun Task5ForegroundScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var seconds by remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                seconds = intent?.getIntExtra(ForegroundCounterService.EXTRA_SECONDS, 0) ?: 0
            }
        }

        ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(ForegroundCounterService.BROADCAST_ACTION),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Задание 5. Foreground service", style = MaterialTheme.typography.titleLarge)
        Text("Прошло секунд: $seconds", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = {
            val intent = Intent(context, ForegroundCounterService::class.java).apply {
                action = ForegroundCounterService.ACTION_START
            }
            context.startForegroundService(intent)
        }) {
            Text("Старт")
        }

        Button(onClick = {
            val intent = Intent(context, ForegroundCounterService::class.java).apply {
                action = ForegroundCounterService.ACTION_STOP
            }
            context.startService(intent)
        }) {
            Text("Стоп")
        }
    }
}