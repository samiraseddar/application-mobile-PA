package com.example.mobileapplication.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mobileapplication.R
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.viewmodel.UserViewModel

@Composable
fun LoginScreen(viewModel: UserViewModel, navController: NavController) {
    var loginValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    val infos = viewModel.infos.observeAsState()
    val userId = viewModel.userId.observeAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.weight(2f)) {
            BackgroundImage()
        }
        Column(modifier = Modifier.weight(3f).padding(24.dp)) {
            Text("Login", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            LoginTextField(value = loginValue, onValueChange = { loginValue = it }, icon = Icons.Outlined.Person, label = "Email")
            Spacer(modifier = Modifier.height(8.dp))
            PasswordTextField(value = passwordValue, onValueChange = { passwordValue = it })
            Spacer(modifier = Modifier.height(16.dp))
            LoginButton(
                enabled = loginValue.isNotBlank() && passwordValue.isNotBlank(),
                onClick = {
                    viewModel.login(LoginDTO(mail = loginValue, password = passwordValue), context)
                }
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = { navController.navigate("register") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("S'inscrire")
            }
        }
    }

    LaunchedEffect(infos.value) {
        infos.value?.let {
            Toast.makeText(context, "Bonjour ${it.firstName} ${it.lastName}", Toast.LENGTH_LONG).show()
            context.getSharedPreferences("token", 0).edit().apply {
                putString("token", it.token)
                userId.value?.let { id -> putLong("id", id) }
                apply()
            }
            navController.navigate("accueil") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    LaunchedEffect(userId.value) {
        if (userId.value == 0L) {
            Toast.makeText(context, "Identifiants erronés", Toast.LENGTH_LONG).show()
        }
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(R.drawable.background),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clip(GenericShape { size, _ ->
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height * 0.75f)
                quadraticBezierTo(size.width / 2, size.height * 1.2f, 0f, size.height * 0.75f)
                close()
            }),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun LoginTextField(value: String, onValueChange: (String) -> Unit, icon: ImageVector, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(icon, contentDescription = null) },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20)
    )
}

@Composable
private fun PasswordTextField(value: String, onValueChange: (String) -> Unit) {
    var isVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(
                    painter = painterResource(if (isVisible) R.drawable.baseline_visibility_off_24 else R.drawable.baseline_visibility_24),
                    contentDescription = null
                )
            }
        },
        label = { Text("Password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20)
    )
}

@Composable
private fun LoginButton(enabled: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        )
    ) {
        Text("Login")
    }
}