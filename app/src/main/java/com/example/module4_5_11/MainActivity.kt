package com.example.module4_5_11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import com.example.module4_5_11.ui.Task5ForegroundScreen
import com.example.module4_5_11.ui.Task6BackgroundTimerScreen
import com.example.module4_5_11.ui.Task7BoundServiceScreen
import com.example.module4_5_11.ui.AppScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    var currentScreen by remember { mutableStateOf(AppScreen.Task5) }

                    Scaffold(
                        bottomBar = {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.Task5,
                                    onClick = { currentScreen = AppScreen.Task5 },
                                    icon = { Icon(Icons.Default.Notifications, null) },
                                    label = { Text("5") }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.Task6,
                                    onClick = { currentScreen = AppScreen.Task6 },
                                    icon = { Icon(Icons.Default.Sync, null) },
                                    label = { Text("6") }
                                )
                                NavigationBarItem(
                                    selected = currentScreen == AppScreen.Task7,
                                    onClick = { currentScreen = AppScreen.Task7 },
                                    icon = { Icon(Icons.Default.Build, null) },
                                    label = { Text("7") }
                                )
                            }
                        }
                    ) { innerPadding ->
                        when (currentScreen) {
                            AppScreen.Task5 -> Task5ForegroundScreen(Modifier.padding(innerPadding))
                            AppScreen.Task6 -> Task6BackgroundTimerScreen(Modifier.padding(innerPadding))
                            AppScreen.Task7 -> Task7BoundServiceScreen(Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}