package com.example.mobileapplication.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.mobileapplication.screens.AccueilScreen
import com.example.mobileapplication.viewmodel.UserViewModel

class AccueilActivity : ComponentActivity() {
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(UserViewModel::class.java)

        setContent {
            AccueilScreen(viewModel)
        }
    }
}