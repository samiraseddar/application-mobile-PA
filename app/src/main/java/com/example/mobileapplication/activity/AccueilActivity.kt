package com.example.mobileapplication.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobileapplication.screens.AccueilScreen
import com.example.mobileapplication.ui.theme.MobileApplicationTheme
import com.example.mobileapplication.viewmodel.UserViewModel
import com.example.mobileapplication.viewmodel.ScriptViewModel

class AccueilActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userViewModel: UserViewModel = viewModel()
                    val scriptViewModel: ScriptViewModel = viewModel()
                    AccueilScreen(userViewModel, scriptViewModel)
                }
            }
        }
    }
}