package com.example.mobileapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.mobileapplication.screens.RegisterScreen
import com.example.mobileapplication.viewmodel.UserViewModel

class InscriptionActivity : ComponentActivity() {
    private val viewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           RegisterScreen(viewModel)
        }
    }
}
