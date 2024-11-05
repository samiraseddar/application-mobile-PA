package com.example.mobileapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobileapplication.components.Sidebar
import com.example.mobileapplication.screens.AccueilScreen
import com.example.mobileapplication.screens.LoginScreen
import com.example.mobileapplication.screens.ProfileScreen
import com.example.mobileapplication.screens.RegisterScreen
import com.example.mobileapplication.ui.theme.MobileApplicationTheme
import com.example.mobileapplication.viewmodel.ProfileViewModel
import com.example.mobileapplication.viewmodel.UserViewModel
import androidx.compose.runtime.CompositionLocalProvider
import com.example.mobileapplication.screens.NewPostScreen
import com.example.mobileapplication.ui.components.LocalNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent() {
    val navController = rememberNavController()
    val viewModel: UserViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                LoginScreen(viewModel, navController)
            }
            composable("accueil") {
                Sidebar(navController) {
                    AccueilScreen(viewModel)
                }
            }
            composable("nouveau_post") {
                Sidebar(navController) {
                    NewPostScreen(viewModel,navController)
                }
            }
            composable("profile/{userId}") { backStackEntry ->
                val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
                Sidebar(navController) {
                    ProfileScreen(userId = userId, viewModel = profileViewModel)
                }
            }
            composable("register") { RegisterScreen(viewModel) }

        }
    }


}