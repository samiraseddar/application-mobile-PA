package com.example.mobileapplication.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
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
import retrofit2.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mobileapplication.dto.script.ScriptRequest
import com.example.mobileapplication.repository.ScriptRepository


class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = UserRepository(application.applicationContext)

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus : LiveData<Boolean> = _registerStatus

    private val _followStatus = MutableLiveData<Boolean>()
    val followStatus : LiveData<Boolean> = _followStatus

    private val _messageStatus = MutableLiveData<Boolean>()
    val messageStatus : LiveData<Boolean> = _messageStatus

    private val _searchResults = MutableStateFlow<List<UserInfoDto>>(emptyList())
    val searchResults: StateFlow<List<UserInfoDto>> = _searchResults

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    private val _infos = MutableLiveData<LoginResponseDTO>()
    val infos : LiveData<LoginResponseDTO> = _infos

    private val _userId = MutableLiveData<Long>()
    val userId : LiveData<Long> = _userId

    private val _userInfo = MutableLiveData<UserInfoDto>()
    val userInfo : LiveData<UserInfoDto> = _userInfo

    private val scriptRepository = ScriptRepository(application.applicationContext)

    private val _scripts = MutableLiveData<List<ScriptRequest>>()
    val scripts: LiveData<List<ScriptRequest>> = _scripts

    private val _scriptCreationStatus = MutableLiveData<Boolean>()
    val scriptCreationStatus: LiveData<Boolean> = _scriptCreationStatus

    fun fetchScripts() {
        viewModelScope.launch {
            try {
                scriptRepository.fetchScripts()
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error fetching scripts", e)
            }
        }
    }

    init {
        scriptRepository.scripts.observeForever { scripts ->
            _scripts.value = scripts
        }
    }
    fun resetScriptCreationStatus() {
        _scriptCreationStatus.value = null
    }

    fun createScript(scriptRequest: ScriptRequest) {
        viewModelScope.launch {
            val response = scriptRepository.createScript(scriptRequest)
            _scriptCreationStatus.value = response != null
            Log.d("UserViewModel", "Script creation status: ${_scriptCreationStatus.value}")
        }
    }
    override fun onCleared() {
        super.onCleared()
        scriptRepository.scripts.removeObserver { }
    }


    fun login(loginDTO: LoginDTO, context: Context) {
       viewModelScope.launch {
           try{
               val responseDTO = userRepository.login(loginDTO)
               Log.d("connexion", "Connexion réussie")
               _infos.value = responseDTO
               _userId.value = responseDTO.userId

               val sharedPreferences = context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)

               responseDTO?.nbFollowers?.let {
                   sharedPreferences.edit().putInt("nbFollowers", it).apply() }
               responseDTO?.nbFollowing?.let {
                   sharedPreferences.edit().putInt("nbFollowing", it).apply() }
               responseDTO?.nbPosts?.let {
                   sharedPreferences.edit().putInt("nbPosts", it).apply() }
               responseDTO?.userId?.let {
                   sharedPreferences.edit().putLong("userId", it).apply() }
               sharedPreferences.edit().putString("firstName", responseDTO?.firstName).apply()
               sharedPreferences.edit().putString("lastName", responseDTO?.lastName).apply()
               sharedPreferences.edit().putString("token", responseDTO?.token).apply()
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

    fun getUsersInfo(id: Long, token: String) {
        viewModelScope.launch {
            try {
                val userInfo = userRepository.getUsersInfo(id, token).await()
                _userInfo.value = userInfo
            } catch (e: Exception) {
                Log.d("USER_VIEW_MODEL",_userInfo.toString())
                e.printStackTrace()
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

    fun updateSearchQuery(query: String) {
        _searchResults.value = emptyList()
        _searchQuery.value = query
    }
    fun resetSearchQuery(){
        _searchResults.value = emptyList()
    }
    fun searchUsers() {
        viewModelScope.launch {
            try {
                val results = userRepository.searchUsers(_searchQuery.value)
                _searchResults.value = results
                Log.d("SEARCH",_searchResults.toString())

            } catch (e: Exception) {
                Log.d("SEARCH",e.toString())
                _searchResults.value = emptyList()
            }
        }
    }


}
