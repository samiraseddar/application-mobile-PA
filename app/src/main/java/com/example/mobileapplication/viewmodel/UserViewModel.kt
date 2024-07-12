package com.example.mobileapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.repository.UserRepository

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val loginResponse: LiveData<LoginResponseDTO?> get() = userRepository.loginResponse
    val registerResponse: LiveData<RegisterResponseDTO?> get() = userRepository.registerResponse

   fun login(loginDTO: LoginDTO) {
        userRepository.login(loginDTO)
    }

 suspend   fun register(registerDTO: RegisterDTO) {
        userRepository.register(registerDTO)
    }
}
