package com.example.mobileapplication.activity
import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mobileapplication.viewmodel.UserViewModel

class LoginActivity : ComponentActivity() {
    private val viewModel : UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           LoginScreen(viewModel)
        }
    }
}
