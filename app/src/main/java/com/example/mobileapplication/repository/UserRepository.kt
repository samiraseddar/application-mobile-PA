package com.example.mobileapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService) {

    private val _loginResponse = MutableLiveData<LoginResponseDTO?>()
    val loginResponse: LiveData<LoginResponseDTO?> get() = _loginResponse

    private val _registerResponse = MutableLiveData<RegisterResponseDTO?>()
    val registerResponse: LiveData<RegisterResponseDTO?> get() = _registerResponse

 fun login(loginDTO: LoginDTO) {
        val call = apiService.login(loginDTO)
        call?.enqueue(object : Callback<LoginResponseDTO?> {
            override fun onResponse(call: Call<LoginResponseDTO?>, response: Response<LoginResponseDTO?>) {
                _loginResponse.value = response.body()
            }

            override fun onFailure(call: Call<LoginResponseDTO?>, t: Throwable) {
                _loginResponse.value = null
            }
        })
    }

suspend    fun register(registerDTO: RegisterDTO) {
        val call = apiService.register(registerDTO)
        call?.enqueue(object : Callback<RegisterResponseDTO?> {
            override fun onResponse(call: Call<RegisterResponseDTO?>, response: Response<RegisterResponseDTO?>) {
                _registerResponse.value = response.body()
            }

            override fun onFailure(call: Call<RegisterResponseDTO?>, t: Throwable) {
                _registerResponse.value = null
            }
        })
    }
}
