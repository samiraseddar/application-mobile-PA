import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.InscriptionActivity
import com.example.mobileapplication.R
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.repository.UserRepository
import com.example.mobileapplication.service.ApiInstance
import com.example.mobileapplication.viewmodel.UserViewModel

@Composable
fun LoginScreen() {
    var loginValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Box(modifier = Modifier.weight(2f)) {
            ShapeImage()
        }

        BoxWithConstraints(modifier = Modifier.weight(3f)) {
            val maxW = maxWidth
            val maxH = maxHeight

            Column(modifier = Modifier.fillMaxSize().padding(maxWidth / 25)) {
                var fontSize = with(LocalDensity.current) { maxW.toSp() * 0.08 }
                Text(
                    text = "Login",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )

                LoginField(
                    modifier = Modifier.fillMaxWidth().padding(top = maxH / 40, bottom = maxH / 40),
                    loginValue = loginValue,
                    onSubmit = { loginValue = it }
                )

                PasswordField(
                    modifier = Modifier.fillMaxWidth().padding(top = maxH / 40, bottom = maxH / 40),
                    passwordValue = passwordValue,
                    onSubmit = { passwordValue = it }
                )

                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.05 }
                LoginButton(fontSize = fontSize, enabled = loginValue.isNotBlank() && passwordValue.isNotBlank()) {
                    val loginDTO = LoginDTO().apply {
                        mail = loginValue
                        password = passwordValue
                    }
                }

                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.04 }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val context = LocalContext.current
                    TextButton(onClick = { context.startActivity(Intent(context, InscriptionActivity::class.java)) }) {
                        Text(text = "New User ? ", fontSize = fontSize, color = Color.DarkGray, fontStyle = FontStyle.Normal)
                        Text(text = "Sign up", fontSize = fontSize)
                    }
                }
            }
        }
    }

 /*   val loginResponse by userViewModel.loginResponse.collectAsStateWithLifecycle()
    loginResponse?.let {
        // Handle the login response
        // You can navigate to another screen or show a message based on the login response
    }

  */
}

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
private fun ShapeImage() {
    val context = LocalContext.current
    val imageResId = R.drawable.background
    val screenWidth = context.resources.displayMetrics.widthPixels.pxToDp()
    val screenHeight = context.resources.displayMetrics.heightPixels.pxToDp()

    val myShape = GenericShape { size, _ ->
        moveTo(0f, 0f)
        lineTo(size.width, 0f)
        lineTo(size.width, 3 * (size.height / 4))
        quadraticBezierTo(
            size.width / 2, 1.2f * size.height,
            0f, 3 * (size.height) / 4
        )
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
private fun LoginField(modifier: Modifier, loginValue: String, onSubmit: (String) -> Unit) {
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
        value = loginValue,
        shape = RoundedCornerShape(20),
        onValueChange = onSubmit,
        singleLine = true,
        label = { Text("Email") }
    )
}

@Composable
private fun PasswordField(modifier: Modifier, passwordValue: String, onSubmit: (String) -> Unit) {
    var isVisible by remember { mutableStateOf(false) }

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
            onDone = { }
        ),
        singleLine = true,
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        label = { Text("Password") }
    )
}

@Composable
private fun LoginButton(modifier: Modifier = Modifier, fontSize: TextUnit, enabled: Boolean = true, onSubmit: () -> Unit) {
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
            text = "Login",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize
        )
    }
}
