package com.example.mobileapplication.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobileapplication.screens.RegisterScreen

//import com.example.mobileapplication.screens.LoginScreen

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           RegisterScreen()
        }
    }
}
