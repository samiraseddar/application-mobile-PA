package com.example.mobileapplication.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileapplication.dto.script.ScriptDTO
import com.example.mobileapplication.dto.script.ScriptRequest
import com.example.mobileapplication.network.RetrofitClient

class ScriptRepository(private val context: Context) {
    private val apiService = RetrofitClient.client
    private val _scripts = MutableLiveData<List<ScriptRequest>>()
    val scripts: LiveData<List<ScriptRequest>> get() = _scripts

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

    suspend fun createScript(scriptRequest: ScriptRequest): ScriptDTO? {
        return try {
            val token = getToken() ?: throw IllegalStateException("Token not found")
            val response = apiService.createScript("Bearer $token", scriptRequest)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            Log.e("ScriptRepository", "Error creating script", e)
            null
        }
    }

}