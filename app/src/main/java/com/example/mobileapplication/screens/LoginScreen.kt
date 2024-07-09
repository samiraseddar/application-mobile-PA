import android.content.Intent
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
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.mobileapplication.activity.RegisterActivity
import com.example.mobileapplication.R

@Composable
fun LoginScreen() {
    var loginValue by remember {
        mutableStateOf("")
    }

    var passwordValue by remember {
        mutableStateOf("")
    }

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
                .fillMaxSize()
                .padding(maxWidth / 25)) {

                var fontSize = with(LocalDensity.current) { maxW.toSp() * 0.08 }
                Text(text = "Login",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Serif
                )

                LoginField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40),
                    loginValue = loginValue,
                    onSubmit = { loginValue = it }
                )

                PasswordField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = maxH / 40, bottom = maxH / 40),
                    passwordValue = passwordValue,
                    onSubmit = { passwordValue = it }
                )

                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.05 }
                    LoginButton(fontSize = fontSize, enabled = loginValue.isNotBlank() && passwordValue.isNotBlank()) {

                    }

                fontSize = with(LocalDensity.current) { maxW.toSp() * 0.04 }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val context = LocalContext.current

                    TextButton(
                        onClick = {context.startActivity(Intent(context, RegisterActivity::class.java)) }
                    ) {
                        Text(text = "New User ? ", fontSize = fontSize, color = Color.DarkGray, fontStyle = FontStyle.Normal)
                        Text(text = "Sign up", fontSize = fontSize)
                    }
                }
            }
        }
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
        label = { Text("password") },
        //colors = MaterialTheme.colorScheme.primary
    )
}
@Composable
private fun LoginButton(modifier: Modifier = Modifier, fontSize: TextUnit, enabled: Boolean=true, onSubmit: () -> Unit) {
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
