package com.example.mobileapplication.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileapplication.dto.script.ScriptCreateRequestDTO
import com.example.mobileapplication.dto.script.ScriptDTO
import com.example.mobileapplication.dto.script.ScriptResponseDTO
import com.example.mobileapplication.network.RetrofitClient

class ScriptRepository(private val context: Context) {
    private val apiService = RetrofitClient.client
    private val _scripts = MutableLiveData<List<ScriptResponseDTO>>()
    val scripts: LiveData<List<ScriptResponseDTO>> get() = _scripts

    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("userInfos", Context.MODE_PRIVATE)
        return sharedPreferences.getString("token", null)
    }

    suspend fun fetchScripts() {
        try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.getScripts("Bearer $token")
            _scripts.value = response.body() ?: emptyList()
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error fetching scripts", e)
            _scripts.value = emptyList()
            throw e
        }
    }

    suspend fun createScript(scriptRequest: ScriptCreateRequestDTO): ScriptDTO? {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.createScript("Bearer $token", scriptRequest)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error creating script", e)
            null
        }
    }

    suspend fun getScriptContent(scriptId: Long): String? {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.getScriptContent(scriptId, "Bearer $token")
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("ScriptRepository", "Error getting content: ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error getting script content", e)
            null
        }
    }

    suspend fun fetchPrivateScripts() {
        try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.getPrivateScripts("Bearer $token")
            _scripts.value = response.body() ?: emptyList()
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error fetching private scripts", e)
            _scripts.value = emptyList()
            throw e
        }
    }

    suspend fun isLiked(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.isLiked(scriptId, "Bearer $token")
            response.body() ?: false
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error checking like status", e)
            false
        }
    }

    suspend fun isDisliked(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.isDisliked(scriptId, "Bearer $token")
            response.body() ?: false
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error checking dislike status", e)
            false
        }
    }

    suspend fun likeScript(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            apiService.likeScript(scriptId, "Bearer $token").isSuccessful
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error liking script", e)
            false
        }
    }

    suspend fun dislikeScript(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            apiService.dislikeScript(scriptId, "Bearer $token").isSuccessful
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error disliking script", e)
            false
        }
    }

    suspend fun removeLike(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            apiService.removeLike(scriptId, "Bearer $token").isSuccessful
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error removing like", e)
            false
        }
    }

    suspend fun removeDislike(scriptId: Long): Boolean {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            apiService.removeDislike(scriptId, "Bearer $token").isSuccessful
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error removing dislike", e)
            false
        }
    }
}