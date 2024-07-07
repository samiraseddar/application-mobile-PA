package com.example.mobileapplication.Activity
import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.mobileapplication.screens.RegisterScreen

//import com.example.mobileapplication.screens.LoginScreen

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           LoginScreen()
        }
    }
}
