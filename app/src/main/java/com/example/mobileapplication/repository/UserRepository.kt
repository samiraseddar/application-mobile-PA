package com.example.mobileapplication.repository


import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.dto.UserInfoDto
import com.example.mobileapplication.network.RetrofitClient
import retrofit2.Call


class UserRepository() {
    private val apiService = RetrofitClient.client

    suspend fun login(loginDTO: LoginDTO) : LoginResponseDTO {
        return apiService.login(loginDTO)
    }

    suspend fun register(registerDTO: RegisterDTO) {
        apiService.register(registerDTO)
    }

    suspend fun getUsersInfo(id: Long, token: String): Call<UserInfoDto> {
        return apiService.getUsersInfo(id, "BEARER $token")
    }

    suspend fun followUser(id: Long, userId: Long, token: String) {
        apiService.followUser(id, userId, "BEARER $token")
    }
    suspend fun unfollowUser(id: Long, userId: Long, token: String) {
        apiService.unfollowUser(id, userId, "BEARER $token")
    }
    suspend fun sendMessage(id: Long, userId: Long, msg : MessageDTO, token: String) {
        apiService.sendMessage(id, userId, msg,"BEARER $token")
    }
}
