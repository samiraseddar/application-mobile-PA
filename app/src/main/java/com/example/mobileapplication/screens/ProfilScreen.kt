package com.example.mobileapplication.screens
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mobileapplication.viewmodel.ProfileViewModel
import androidx.compose.ui.platform.LocalContext
import android.content.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(userId: Long, viewModel: ProfileViewModel) {
    val userInfo by viewModel.userInfo.observeAsState()
    val isFollowing by viewModel.isFollowing.observeAsState()
    val context = LocalContext.current
    val currentUserId = remember {
        context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)
            .getLong("userId", 0L)
    }

    LaunchedEffect(userId) {
        viewModel.getUserInfo(userId)
        viewModel.checkFollowStatus(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        userInfo?.let { info ->
            Text(
                text = "${info.firstName} ${info.lastName}",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${info.nbFollowers}")
                    Text(text = "Abonnés")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${info.nbFollowing}")
                    Text(text = "Abonnements")
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "${info.nbPosts}")
                    Text(text = "Posts")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (userId != currentUserId) {
                Button(
                    onClick = {
                        if (isFollowing == true) {
                            viewModel.unfollowUser(userId)
                        } else {
                            viewModel.followUser(userId)
                            Log.d("FOLLOWING",isFollowing.toString())
                        }
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(
                        text = if (isFollowing == true) "Unfollow" else "Follow",
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Informations Personnelles",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Nom: ${info.lastName}")
            Text(text = "Prénom: ${info.firstName}")
        }
    }
}