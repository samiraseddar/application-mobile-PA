package com.example.mobileapplication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.mobileapplication.dto.script.ScriptDTO
import com.example.mobileapplication.dto.script.ScriptRequest
import com.example.mobileapplication.viewmodel.UserViewModel
import androidx.compose.material3.TextField
import androidx.navigation.NavController

@Composable
fun NewPostScreen(viewModel: UserViewModel, navController: NavController) {
    val context = LocalContext.current
    var content by remember { mutableStateOf(TextFieldValue("")) }
    val scriptCreationStatus by viewModel.scriptCreationStatus.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Contenu du script") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Button(onClick = {
            val scriptRequest = ScriptRequest(
                scriptDTO = ScriptDTO(
                    id = 0L,
                    name = "helloWorld",
                    location = "",
                    protectionLevel = "PRIVATE",
                    language = "Python",
                    inputFileExtensions = "",
                    outputFileNames = "",
                    userId = 1L,
                    nbLikes = 0,
                    nbDislikes = 0
                ),
                scriptContent = content.text
            )
            viewModel.createScript(scriptRequest)
        }) {
            Text("Créer un post")
        }

        LaunchedEffect(scriptCreationStatus) {
            if (scriptCreationStatus == true) {
                Toast.makeText(context, "Post créé avec succès", Toast.LENGTH_SHORT).show()
                navController.navigate("accueil") { popUpTo("accueil") { inclusive = true } }
                viewModel.resetScriptCreationStatus()
            }
        }
    }
}
