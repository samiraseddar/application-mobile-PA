package com.example.mobileapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.dto.UserInfoDto
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.foundation.clickable
import androidx.compose.runtime.remember

@Composable
fun SearchUserEngine(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    searchResults: List<UserInfoDto>,

) {
    Column {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            label = { Text("Rechercher des utilisateurs") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn {
            items(searchResults) { user ->
                UserSearchItem(user)
            }
        }
    }
}

@Composable
fun UserSearchItem(user: UserInfoDto) {
    val navController = LocalNavController.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate("profile/${user.userId}")
            }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}