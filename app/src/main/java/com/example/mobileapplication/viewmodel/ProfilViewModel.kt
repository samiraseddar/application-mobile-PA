package com.example.mobileapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.dto.UserInfoDto
import com.example.mobileapplication.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.await
import android.util.Log

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(application.applicationContext)
    private val _userInfo = MutableLiveData<UserInfoDto>()
    val userInfo: LiveData<UserInfoDto> = _userInfo
    private val _isFollowing = MutableLiveData<Boolean>()
    val isFollowing: LiveData<Boolean> = _isFollowing
    fun getUserInfo(userId: Long) {
        viewModelScope.launch {
            try {
                val token = getTokenFromSharedPreferences()
                val response = userRepository.getUsersInfo(userId, token).await()
                _userInfo.value = response
                Log.d("PROFILE_VIEW_MODEL",_userInfo.toString())
                Log.d("PROFILE_VIEW_MODEL",token)
            } catch (e: Exception) {
                Log.d("PROFILE_VIEW_MODEL",e.toString())
            }
        }
    }

    private fun getTokenFromSharedPreferences(): String {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("userInfos", Application.MODE_PRIVATE)
        return sharedPreferences.getString("token", "") ?: ""
    }


    fun checkFollowStatus(userId: Long) {
        viewModelScope.launch {
            try {
                val isFollowing = userRepository.isFollowing(userId)
                _isFollowing.value = isFollowing
            } catch (e: Exception) {
                Log.d("PROFILE_VIEW_MODEL_IS_FOLOW", "Error checking follow status: ${e.message}")
            }
        }
    }

    fun followUser(userId: Long) {
        viewModelScope.launch {
            try {
                userRepository.followUser(userId)
                _isFollowing.value = true
                _userInfo.value = _userInfo.value?.copy(nbFollowers = (_userInfo.value?.nbFollowers ?: 0) + 1)
                Log.d("P_V_MODEL",isFollowing.toString())

            } catch (e: Exception) {
                Log.d("PROFILE_VIEW_MODEL", "Error following user: ${e.message}")
            }
        }
    }

    fun unfollowUser(userId: Long) {
        viewModelScope.launch {
            try {
                userRepository.unfollowUser(userId)
                _isFollowing.value = false
                _userInfo.value = _userInfo.value?.copy(nbFollowers = (_userInfo.value?.nbFollowers ?: 0) - 1)
                Log.d("P_V_MODEL",isFollowing.toString())
            } catch (e: Exception) {
                Log.d("PROFILE_VIEW_MODEL", "Error unfollowing user: ${e.message}")
            }
        }
    }

}