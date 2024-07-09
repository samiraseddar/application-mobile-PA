package com.example.mobileapplication.service

import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST("/api/login")
    fun login(@Body loginDTO: LoginDTO?): Call<LoginResponseDTO?>?

    @POST("/api/register")
    fun register(@Body registerDTO: RegisterDTO?): Call<RegisterResponseDTO?>?
}
