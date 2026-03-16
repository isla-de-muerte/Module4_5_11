package com.example.module4_5_11.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.module4_5_11.service.BackgroundTimerService

@Composable
fun Task6BackgroundTimerScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var input by remember { mutableStateOf("10") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Задание 6. Background timer", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Количество секунд") },
            singleLine = true
        )

        Button(onClick = {
            val seconds = input.toIntOrNull() ?: 0
            if (seconds > 0) {
                val intent = Intent(context, BackgroundTimerService::class.java).apply {
                    putExtra(BackgroundTimerService.EXTRA_DURATION, seconds)
                }
                context.startService(intent)
            }
        }) {
            Text("Запустить таймер")
        }
    }
}