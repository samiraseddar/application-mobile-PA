package com.example.mobileapplication.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mobileapplication.dto.script.ScriptCreateDTO
import com.example.mobileapplication.dto.script.ScriptCreateRequestDTO
import com.example.mobileapplication.dto.script.ScriptResponseDTO
import com.example.mobileapplication.repository.ScriptRepository
import kotlinx.coroutines.launch

class ScriptViewModel(application: Application) : AndroidViewModel(application) {
    private val scriptRepository = ScriptRepository(application.applicationContext)

    private val _scripts = MutableLiveData<List<ScriptResponseDTO>>()
    val scripts: LiveData<List<ScriptResponseDTO>> = _scripts

    private val _scriptCreationStatus = MutableLiveData<Boolean>()
    val scriptCreationStatus: LiveData<Boolean> = _scriptCreationStatus

    private val _scriptContents = MutableLiveData<Map<Long, String>>()
    val scriptContents: LiveData<Map<Long, String>> = _scriptContents

    private val _likeStates = MutableLiveData<Map<Long, Boolean>>(mapOf())
    val likeStates: LiveData<Map<Long, Boolean>> = _likeStates

    private val _dislikeStates = MutableLiveData<Map<Long, Boolean>>(mapOf())
    val dislikeStates: LiveData<Map<Long, Boolean>> = _dislikeStates

    init {
        scriptRepository.scripts.observeForever { scripts ->
            _scripts.value = scripts
        }
    }

    fun fetchScripts() {
        viewModelScope.launch {
            try {
                scriptRepository.fetchScripts()
            } catch (e: Exception) {
                Log.e("ScriptViewModel", "Error fetching scripts", e)
                _scripts.value = emptyList()
            }
        }
    }

    fun resetScriptCreationStatus() {
        _scriptCreationStatus.value = null
    }

    fun createScript(name: String, content: String, language: String = "Python") {
        viewModelScope.launch {
            try {
                val scriptCreateDTO = ScriptCreateDTO(
                    name = name,
                    protectionLevel = "PRIVATE",
                    language = language,
                    inputFiles = "",
                    outputFiles = ""
                )
                
                val scriptRequest = ScriptCreateRequestDTO(
                    scriptDTO = scriptCreateDTO,
                    scriptContent = content
                )
                
                val response = scriptRepository.createScript(scriptRequest)
                _scriptCreationStatus.value = response != null
                if (response != null) {
                    fetchScripts()
                }
            } catch (e: Exception) {
                Log.e("ScriptViewModel", "Error creating script", e)
                _scriptCreationStatus.value = false
            }
        }
    }

    fun fetchScriptContent(scriptId: Long) {
        viewModelScope.launch {
            try {
                val content = scriptRepository.getScriptContent(scriptId)
                val currentContents = _scriptContents.value?.toMutableMap() ?: mutableMapOf()
                if (content != null) {
                    currentContents[scriptId] = content
                    _scriptContents.value = currentContents
                    Log.d("ScriptViewModel", "Content loaded for script $scriptId: $content")
                } else {
                    Log.e("ScriptViewModel", "Failed to load content for script $scriptId")
                }
            } catch (e: Exception) {
                Log.e("ScriptViewModel", "Error fetching script content for $scriptId", e)
            }
        }
    }

    fun getUserId(): Long {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("userInfos", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("userId", -1L)
    }

    fun fetchPrivateScripts() {
        viewModelScope.launch {
            try {
                scriptRepository.fetchPrivateScripts()
            } catch (e: Exception) {
                Log.e("ScriptViewModel", "Error fetching private scripts", e)
                _scripts.value = emptyList()
            }
        }
    }

    fun checkLikeStatus(scriptId: Long) {
        viewModelScope.launch {
            val isLiked = scriptRepository.isLiked(scriptId)
            val currentStates = _likeStates.value?.toMutableMap() ?: mutableMapOf()
            currentStates[scriptId] = isLiked
            _likeStates.value = currentStates
        }
    }

    fun checkDislikeStatus(scriptId: Long) {
        viewModelScope.launch {
            val isDisliked = scriptRepository.isDisliked(scriptId)
            val currentStates = _dislikeStates.value?.toMutableMap() ?: mutableMapOf()
            currentStates[scriptId] = isDisliked
            _dislikeStates.value = currentStates
        }
    }

    fun toggleLike(scriptId: Long) {
        viewModelScope.launch {
            val isCurrentlyLiked = _likeStates.value?.get(scriptId) ?: false
            val success = if (isCurrentlyLiked) {
                scriptRepository.removeLike(scriptId)
            } else {
                scriptRepository.likeScript(scriptId)
            }
            if (success) {
                fetchScripts()
                checkLikeStatus(scriptId)
                checkDislikeStatus(scriptId)
            }
        }
    }

    fun toggleDislike(scriptId: Long) {
        viewModelScope.launch {
            val isCurrentlyDisliked = _dislikeStates.value?.get(scriptId) ?: false
            val success = if (isCurrentlyDisliked) {
                scriptRepository.removeDislike(scriptId)
            } else {
                scriptRepository.dislikeScript(scriptId)
            }
            if (success) {
                fetchScripts()
                checkLikeStatus(scriptId)
                checkDislikeStatus(scriptId)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scriptRepository.scripts.removeObserver { }
    }
} 