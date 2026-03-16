package com.example.module4_5_11.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
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
import com.example.module4_5_11.service.RandomNumberBoundService
import kotlinx.coroutines.delay

@Composable
fun Task7BoundServiceScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var boundService by remember { mutableStateOf<RandomNumberBoundService?>(null) }
    var isBound by remember { mutableStateOf(false) }
    var currentNumber by remember { mutableStateOf(0) }

    val connection = remember {
        object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as RandomNumberBoundService.LocalBinder
                boundService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                isBound = false
                boundService = null
            }
        }
    }

    LaunchedEffect(isBound, boundService) {
        while (isBound && boundService != null) {
            currentNumber = boundService?.currentNumber ?: 0
            delay(1000)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            if (isBound) {
                context.unbindService(connection)
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Задание 7. Bound service", style = MaterialTheme.typography.titleLarge)
        Text("Текущее число: $currentNumber", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = {
            val intent = Intent(context, RandomNumberBoundService::class.java)
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }) {
            Text("Подключиться")
        }

        Button(onClick = {
            if (isBound) {
                context.unbindService(connection)
                isBound = false
                boundService = null
            }
        }) {
            Text("Отключиться")
        }
    }
}