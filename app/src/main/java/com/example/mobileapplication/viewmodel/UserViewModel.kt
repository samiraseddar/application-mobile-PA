package com.example.mobileapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.dto.UserInfoDto
import com.example.mobileapplication.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class UserViewModel() : ViewModel() {
    private val userRepository = UserRepository()

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus : LiveData<Boolean> = _registerStatus

    private val _followStatus = MutableLiveData<Boolean>()
    val followStatus : LiveData<Boolean> = _followStatus

    private val _messageStatus = MutableLiveData<Boolean>()
    val messageStatus : LiveData<Boolean> = _messageStatus


    private val _infos = MutableLiveData<LoginResponseDTO>()
    val infos : LiveData<LoginResponseDTO> = _infos

    private val _userId = MutableLiveData<Long>()
    val userId : LiveData<Long> = _userId

    private val _userInfo = MutableLiveData<UserInfoDto>()
    val userInfo : LiveData<UserInfoDto> = _userInfo

   fun login(loginDTO: LoginDTO) {
       viewModelScope.launch {
           try{
               val responseDTO = userRepository.login(loginDTO)
               Log.d("connexion", "Connexion réussie")
               _infos.value = responseDTO
               _userId.value = responseDTO.userId
           }catch (e: HttpException){
               Log.d("error", "Echec de la connexion $e")
           }
       }
    }

    fun register(registerDTO: RegisterDTO) {
        viewModelScope.launch {
            try{
                userRepository.register(registerDTO)
                Log.d("register", "Inscription réussie")
                _registerStatus.value = true
            }catch (e: HttpException){
                Log.d("error", "Echec de l'inscription $e")
                _registerStatus.value = false
            }
        }
    }

    fun getUsersInfo(id : Long, token:String) {
        viewModelScope.launch{
            try{
                val userInfo = userRepository.getUsersInfo(id, token)
                Log.d("infos", "Récuperation d'info réussi $userInfo")
                _userInfo.value = userInfo
            }catch (e: HttpException){
                Log.d("error", "Echec de la récupération d'infos $e")
                _registerStatus.value = false
            }
        }
    }

    fun followUser(id : Long, userId : Long, token:String) {
        viewModelScope.launch{
            try{
                 userRepository.followUser(id, userId, token)
                _followStatus.value = true
            }catch (e: HttpException){
                Log.d("error", "Echec du follow $e")
                _followStatus.value = false
            }
        }
    }

    fun unfollowUser(id : Long, userId : Long, token:String) {
        viewModelScope.launch{
            try{
                userRepository.unfollowUser(id, userId, token)
                _followStatus.value = false
            }catch (e: HttpException){
                Log.d("error", "Echec du unfollow $e")
                _followStatus.value = true
            }
        }
    }

    fun sendMessage(id : Long, userId : Long, msg : MessageDTO, token:String) {
        viewModelScope.launch{
            try{
                userRepository.sendMessage(id, userId, msg, token)
                _messageStatus.value = true
            }catch (e: HttpException){
                Log.d("error", "Echec de l'envoi $e")
                _messageStatus.value = false
            }
        }
    }
}
