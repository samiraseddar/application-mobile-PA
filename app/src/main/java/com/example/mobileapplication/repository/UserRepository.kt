package com.example.mobileapplication.repository


import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.dto.UserInfoDto
import com.example.mobileapplication.network.RetrofitClient
import com.example.mobileapplication.viewmodel.UserViewModel
import retrofit2.Call
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import android.util.Log


class UserRepository(private val context: Context) {

    private val apiService = RetrofitClient.client
    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }
    suspend fun login(loginDTO: LoginDTO) : LoginResponseDTO {
        return apiService.login(loginDTO)
    }

    suspend fun register(registerDTO: RegisterDTO) {
        apiService.register(registerDTO)
    }

    suspend fun getUsersInfo(id: Long, token: String): Call<UserInfoDto> {
        return apiService.getUsersInfo(id, "BEARER $token")
    }
    suspend fun searchUsers(query: String): List<UserInfoDto> {
        val token = getToken()
        if (token != null) {
            return apiService.searchUsers(query, "Bearer $token")
        } else {
            throw IllegalStateException("User not authenticated")
        }
    }

    suspend fun isFollowing(userId: Long): Boolean {
        val token = getToken() ?: throw IllegalStateException("Token not found")
        return apiService.isFollowing(userId, "Bearer $token")
    }

    suspend fun followUser(userId: Long) {
        val token = getToken() ?: throw IllegalStateException("Token not found")
        val response = apiService.followUser(userId, "Bearer $token")
        if (!response.isSuccessful) {
            throw Exception("Follow failed with code ${response.code()}")
        }
    }

    suspend fun unfollowUser(userId: Long) {
        val token = getToken() ?: throw IllegalStateException("Token not found")
        val response = apiService.unfollowUser(userId, "Bearer $token")
        if (!response.isSuccessful) {
            throw Exception("Unfollow failed with code ${response.code()}")
        }
    }



    suspend fun sendMessage(id: Long, userId: Long, msg : MessageDTO, token: String) {
        apiService.sendMessage(id, userId, msg,"BEARER $token")
    }
}
