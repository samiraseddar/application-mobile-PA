package com.example.mobileapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.mobileapplication.ui.components.SearchUserEngine
import com.example.mobileapplication.viewmodel.UserViewModel


@Composable
fun AccueilScreen(viewModel: UserViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()

    Column {
        SearchUserEngine(
            searchQuery = searchQuery,
            onSearchQueryChange = { newQuery ->

                viewModel.updateSearchQuery(newQuery)
                viewModel.searchUsers()
            },
            searchResults = searchResults
        )

    }

}