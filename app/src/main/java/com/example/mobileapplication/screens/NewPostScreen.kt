package com.example.mobileapplication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.mobileapplication.viewmodel.ScriptViewModel
import androidx.navigation.NavController
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Composable
fun NewPostScreen(scriptViewModel: ScriptViewModel, navController: NavController) {
    val context = LocalContext.current
    var content by remember { mutableStateOf(TextFieldValue("")) }
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var selectedLanguage by remember { mutableStateOf("Python") }
    val languages = listOf("Python")
    val scriptCreationStatus by scriptViewModel.scriptCreationStatus.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du script") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            var expanded by remember { mutableStateOf(false) }

            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedLanguage)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                languages.forEach { language ->
                    DropdownMenuItem(
                        text = { Text(language) },
                        onClick = {
                            selectedLanguage = language
                            expanded = false
                        }
                    )
                }
            }
        }

        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Contenu du script") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF1E1E1E))
                .padding(bottom = 16.dp),
            textStyle = TextStyle(
                color = Color(0xFFD4D4D4),
                fontFamily = FontFamily.Monospace
            ),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color(0xFFD4D4D4),
                unfocusedTextColor = Color(0xFFD4D4D4),
                focusedContainerColor = Color(0xFF1E1E1E),
                unfocusedContainerColor = Color(0xFF1E1E1E),
                cursorColor = Color(0xFFD4D4D4),
                focusedIndicatorColor = Color(0xFF007ACC),
                unfocusedIndicatorColor = Color(0xFF404040)
            )
        )

        Button(
            onClick = {
                scriptViewModel.createScript(
                    name = name.text,
                    content = content.text,
                    language = selectedLanguage
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Créer un script")
        }

        LaunchedEffect(scriptCreationStatus) {
            if (scriptCreationStatus == true) {
                Toast.makeText(context, "Script créé avec succès", Toast.LENGTH_SHORT).show()
                navController.navigate("accueil") { popUpTo("accueil") { inclusive = true } }
                scriptViewModel.resetScriptCreationStatus()
            } else if (scriptCreationStatus == false) {
                Toast.makeText(context, "Erreur lors de la création du script", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
