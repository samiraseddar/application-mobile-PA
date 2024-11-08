package com.example.mobileapplication.components

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import com.example.mobileapplication.MainActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Sidebar(navController: NavController, content: @Composable () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val context = LocalContext.current
    val activity = LocalContext.current as? ComponentActivity

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(12.dp))
                NavigationDrawerItem(
                    label = { Text("Accueil") },
                    selected = currentRoute == "accueil",
                    onClick = {
                        navController.navigate("accueil")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Nouveau Script") },
                    selected = currentRoute == "nouveau_post",
                    onClick = {
                        navController.navigate("nouveau_post")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Mes Scripts") },
                    selected = currentRoute == "mes_scripts",
                    onClick = {
                        navController.navigate("mes_scripts")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
                NavigationDrawerItem(
                    label = { Text("Mon Profil") },
                    selected = currentRoute?.startsWith("profile") == true,
                    onClick = {
                        val sharedPreferences = context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)
                        val userId = sharedPreferences.getLong("userId", -1L)
                        navController.navigate("profile/$userId")
                        scope.launch { drawerState.close() }
                    },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) }
                )
                
                Spacer(modifier = Modifier.weight(1f))  // Pour pousser le bouton déconnexion en bas
                
                NavigationDrawerItem(
                    label = { Text("Déconnexion") },
                    selected = false,
                    onClick = {
                        scope.launch { 
                            drawerState.close()
                        }
                        // Effacer les SharedPreferences
                        val sharedPreferences = context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)
                        sharedPreferences.edit().clear().commit()
                        
                        // Redémarrer l'activité
                        activity?.let {
                            val intent = Intent(it, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            it.startActivity(intent)
                            it.finish()
                        }
                    },
                    icon = { Icon(Icons.Default.ExitToApp, contentDescription = null) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                content()
            }
        }
    }
}
