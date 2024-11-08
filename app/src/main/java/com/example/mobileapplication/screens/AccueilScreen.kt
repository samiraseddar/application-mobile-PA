package com.example.mobileapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.dto.script.ScriptRequest
import com.example.mobileapplication.dto.script.ScriptResponseDTO
import com.example.mobileapplication.ui.components.SearchUserEngine
import com.example.mobileapplication.viewmodel.UserViewModel
import com.example.mobileapplication.viewmodel.ScriptViewModel

@Composable
fun AccueilScreen(
    userViewModel: UserViewModel,
    scriptViewModel: ScriptViewModel
) {
    val searchQuery by userViewModel.searchQuery.collectAsState()
    val searchResults by userViewModel.searchResults.collectAsState()
    val scripts by scriptViewModel.scripts.observeAsState(initial = emptyList())
    val scriptContents by scriptViewModel.scriptContents.observeAsState(initial = emptyMap())

    LaunchedEffect(Unit) {
        scriptViewModel.fetchScripts()
    }

    Column {
        Text(
            text = "Recherche d'utilisateurs",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        SearchUserEngine(
            searchQuery = searchQuery,
            onSearchQueryChange = { newQuery ->
                userViewModel.updateSearchQuery(newQuery)
                userViewModel.searchUsers()
            },
            searchResults = searchResults
        )

        Text(
            text = "Liste des scripts",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(scripts) { script ->
                ScriptCard(script, scriptViewModel, scriptContents)
            }
        }
    }
}

@Composable
fun ScriptCard(
    script: ScriptResponseDTO,
    scriptViewModel: ScriptViewModel,
    scriptContents: Map<Long, String>
) {
    val currentUserId = scriptViewModel.getUserId()
    
    LaunchedEffect(script.id) {
        if (!scriptContents.containsKey(script.id)) {
            scriptViewModel.fetchScriptContent(script.id)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = script.name,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Langage: ${script.language}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            ) {
                val content = scriptContents[script.id]
                Text(
                    text = when {
                        content != null -> content
                        else -> "Chargement du contenu..."
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (script.userId != currentUserId) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "👍 ${script.nbLikes}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "👎 ${script.nbDislikes}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}