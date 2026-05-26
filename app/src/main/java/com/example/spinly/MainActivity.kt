package com.example.spinly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.spinly.ui.navigation.SpinAppNavHost
import com.example.spinly.ui.theme.SpinAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpinAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SpinAppNavHost()
                }
            }
        }
    }
}