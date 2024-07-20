package com.example.mobileapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel() : ViewModel() {
    private val userRepository = UserRepository()

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus : LiveData<Boolean> = _registerStatus
   fun login(loginDTO: LoginDTO) {
        userRepository.login(loginDTO)
    }

    fun register(registerDTO: RegisterDTO) {
        viewModelScope.launch {
            try{
                userRepository.register(registerDTO)
                _registerStatus.value = true
            }catch (e: HttpException){
                _registerStatus.value = false
            }
        }
    }
}
