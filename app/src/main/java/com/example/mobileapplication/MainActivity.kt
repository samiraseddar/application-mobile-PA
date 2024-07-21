package com.example.mobileapplication
import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileapplication.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {
    private val viewModel : UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(viewModel)
        }
    }
}

@Preview
@Composable
fun Test(){
    Text("test")
}

@Composable
fun MyApp(viewModel : UserViewModel) {
    LoginScreen(viewModel);
}
