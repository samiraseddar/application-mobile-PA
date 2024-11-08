package com.example.mobileapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mobileapplication.dto.script.ScriptResponseDTO
import com.example.mobileapplication.viewmodel.ScriptViewModel

@Composable
fun UserScriptScreen(scriptViewModel: ScriptViewModel) {
    val scripts by scriptViewModel.scripts.observeAsState(initial = emptyList())
    val scriptContents by scriptViewModel.scriptContents.observeAsState(initial = emptyMap())
    var selectedScript by remember { mutableStateOf<ScriptResponseDTO?>(null) }
    
    val userId = scriptViewModel.getUserId()
    
    val userScripts = scripts.filter { it.userId == userId }

    LaunchedEffect(Unit) {
        scriptViewModel.fetchPrivateScripts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Mes Scripts",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(userScripts) { script ->
                UserScriptCard(
                    script = script,
                    onOpenClick = {
                        selectedScript = script
                        if (!scriptContents.containsKey(script.id)) {
                            scriptViewModel.fetchScriptContent(script.id)
                        }
                    }
                )
            }
        }
    }

    selectedScript?.let { script ->
        Dialog(onDismissRequest = { selectedScript = null }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = script.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .defaultMinSize(minHeight = 200.dp),
                        color = Color(0xFF1E1E1E)
                    ) {
                        Text(
                            text = scriptContents[script.id] ?: "Chargement...",
                            color = Color.White,
                            fontFamily = FontFamily.Monospace,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    
                    Button(
                        onClick = { selectedScript = null },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 16.dp)
                    ) {
                        Text("Fermer")
                    }
                }
            }
        }
    }
}

@Composable
fun UserScriptCard(
    script: ScriptResponseDTO,
    onOpenClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = script.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Langage: ${script.language}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Button(onClick = onOpenClick) {
                Text("Ouvrir")
            }
        }
    }
} 