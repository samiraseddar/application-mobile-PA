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
import com.example.mobileapplication.ui.components.SearchUserEngine
import com.example.mobileapplication.viewmodel.UserViewModel

@Composable
fun AccueilScreen(viewModel: UserViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    val scripts by viewModel.scripts.observeAsState(initial = emptyList())

    // Effet pour charger les scripts au démarrage
    LaunchedEffect(Unit) {
        viewModel.fetchScripts()
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
                viewModel.updateSearchQuery(newQuery)
                viewModel.searchUsers()
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
                ScriptCard(script)
            }
        }
    }
}

@Composable
fun ScriptCard(script: ScriptRequest) {
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
                text = script.scriptDTO?.name ?: "Sans nom",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = script.scriptDTO?.location ?: "Emplacement inconnu",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = script.scriptContent ?: "Pas de contenu",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "👍 ${script.scriptDTO?.nbLikes ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "👎 ${script.scriptDTO?.nbDislikes ?: 0}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}