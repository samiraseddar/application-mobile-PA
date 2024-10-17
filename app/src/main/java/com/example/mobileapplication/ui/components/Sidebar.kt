package com.example.mobileapplication.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.content.Context
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sidebar(navController: NavController, content: @Composable () -> Unit) {
    var isOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sharedPreferences = remember { context.getSharedPreferences("userInfos", Context.MODE_PRIVATE) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                    label = { Text("Accueil") },
                    selected = false,
                    onClick = {
                        navController.navigate("accueil")
                        isOpen = false
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    label = { Text("Mon Profil") },
                    selected = false,
                    onClick = {
                        val userId = sharedPreferences.getLong("userId", 0L)
                        navController.navigate("profile/$userId")
                        isOpen = false
                    }
                )
            }
        },
        drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Application") },
                        navigationIcon = {
                            IconButton(onClick = { isOpen = !isOpen }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    content()
                }
            }
        }
    )
}