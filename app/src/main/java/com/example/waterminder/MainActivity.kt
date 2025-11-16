package com.example.waterminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.waterminder.navigation.NavGraph
import com.example.waterminder.ui.theme.AppBackground
import com.example.waterminder.ui.theme.WaterMinderTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            WaterMinderTheme() {
                AppBackground {
                    NavGraph()
                }
            }
        }
    }
}

