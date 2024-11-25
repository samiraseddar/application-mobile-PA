package com.example.mobileapplication.screens

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.activity.LoginActivity
import com.example.mobileapplication.R
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.repository.UserRepository
import com.example.mobileapplication.viewmodel.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(viewModel: UserViewModel) {

    var usernameValue by remember {
        mutableStateOf("")
    }
    var firstnameValue by remember {
        mutableStateOf("")
    }
    var lastnameValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

    var confirmPasswordValue by remember {
        mutableStateOf("")
    }

    val registerStatus = viewModel.registerStatus.observeAsState()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)) {
        Box(modifier = Modifier.weight(2f)) {
            ShapeImage()
        }

        BoxWithConstraints(modifier = Modifier.weight(3f)) {
            val maxW = maxWidth
            val maxH = maxHeight

            Column(modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(maxWidth / 25)) {

                var fontSize = with(LocalDensity.current) { maxW.toSp() * 0.08 }
                Text(text = "Register",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40)
                        .background(color = Color.White, shape = RoundedCornerShape(20)),
                    value = firstnameValue,
                    shape = RoundedCornerShape(20),
                    onValueChange = { firstnameValue = it },
                    singleLine = true,
                    label = { Text("First Name") }
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40)
                        .background(color = Color.White, shape = RoundedCornerShape(20)),
                    value = lastnameValue,
                    shape = RoundedCornerShape(20),
                    onValueChange = { lastnameValue = it },
                    singleLine = true,
                    label = { Text("Last Name") }
                )

                UsernameField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40),
                    usernameValue = usernameValue,
                    onSubmit = { usernameValue = it }
                )

                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40),
                    passwordValue = passwordValue,
                    onSubmit = { passwordValue = it }
                )

                ConfirmPasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40),
                    confirmPasswordValue = confirmPasswordValue,
                    onSubmit = { confirmPasswordValue = it }
                )

                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.05 }
                RegisterButton(fontSize = fontSize,
                    enabled = usernameValue.isNotBlank() && passwordValue.isNotBlank() && confirmPasswordValue.isNotBlank(),
                    onSubmit = {
                        coroutineScope.launch {
                            viewModel.register(RegisterDTO(usernameValue, passwordValue, confirmPasswordValue, lastnameValue, firstnameValue ))
                        }
                    })
                // bouton pour naviguer vers la page de connexion
                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.04 }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val context = LocalContext.current

                    TextButton(
                        onClick = {context.startActivity(Intent(context, LoginActivity::class.java))
                        }
                    ) {
                        Text(text = "Already have an account? ", fontSize = fontSize, color = Color.DarkGray, fontStyle = FontStyle.Normal)
                        Text(text = "Login", fontSize = fontSize)
                    }
                }
            }
        }
    }

    if(registerStatus.value == true){
        Toast.makeText(LocalContext.current, "Inscription réussie", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
private fun ShapeImage() {
    val context = LocalContext.current
    val imageResId = R.drawable.background
    val screenWidth = context.resources.displayMetrics.widthPixels.pxToDp()
    val screenHeight = context.resources.displayMetrics.heightPixels.pxToDp()

    val myShape = GenericShape {
            size, _ ->

        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, 3* (size.height/4))
        quadraticBezierTo(size.width/2, 1.2f * size.height,
            0f, 3*(size.height)/4)

        lineTo(0f, 0f)
        close()
    }

    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = Modifier
            .size(width = screenWidth * 2f, screenHeight * 2f)
            .clip(myShape)
            .shadow(5.dp),
        contentScale = ContentScale.FillBounds
    )
}

@Composable
private fun UsernameField(modifier: Modifier, usernameValue: String, onSubmit: (String) -> Unit) {
    val leadingIcon = @Composable {
        Icon(
            Icons.Outlined.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    OutlinedTextField(
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(20)),
        leadingIcon = leadingIcon,
        value = usernameValue,
        shape = RoundedCornerShape(20),
        onValueChange = onSubmit,
        singleLine = true,
        label = { Text("Email") }
    )
}

@Composable
private fun PasswordField(modifier: Modifier, passwordValue: String, onSubmit: (String) -> Unit) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    val leadingIcon = @Composable {
        Icon(
            Icons.Outlined.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isVisible = !isVisible }) {
            Icon(
                painter = if (isVisible) painterResource(R.drawable.baseline_visibility_off_24) else painterResource(R.drawable.baseline_visibility_24),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        }
    }

    OutlinedTextField(
        value = passwordValue,
        onValueChange = onSubmit,
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(20)),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(20),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {  }
        ),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = { Text("Password") },
    )
}

@Composable
private fun ConfirmPasswordField(modifier: Modifier, confirmPasswordValue: String, onSubmit: (String) -> Unit) {
    var isVisible by remember {
        mutableStateOf(false)
    }

    val leadingIcon = @Composable {
        Icon(
            Icons.Outlined.Lock,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val trailingIcon = @Composable {
        IconButton(onClick = { isVisible = !isVisible }) {
            Icon(
                painter = if (isVisible) painterResource(R.drawable.baseline_visibility_off_24) else painterResource(R.drawable.baseline_visibility_24),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.surfaceTint
            )
        }
    }

    OutlinedTextField(
        value = confirmPasswordValue,
        onValueChange = onSubmit,
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(20)),
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(20),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {  }
        ),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = { Text("Confirm Password") },
    )
}

@Composable
private fun RegisterButton(modifier: Modifier = Modifier, fontSize: TextUnit, enabled: Boolean = true, onSubmit: () -> Unit) {
    TextButton(
        onClick = onSubmit,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFFF82AB),
                        Color(0xFFFFABAB),
                        Color(0xFFDA70D6),
                        Color(0xFF8A2BE2)
                    ),
                ),
                shape = RoundedCornerShape(30)
            ),
    ) {
        Text(
            text = "Register",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}
