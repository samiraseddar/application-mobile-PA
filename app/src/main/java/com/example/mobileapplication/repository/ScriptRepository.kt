package com.example.mobileapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileapplication.dto.script.ScriptDTO
import com.example.mobileapplication.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScriptRepository(private val apiService: ApiService) {

    private val _scripts = MutableLiveData<List<ScriptDTO>>()
    val scripts: LiveData<List<ScriptDTO>> get() = _scripts

    fun fetchScripts() {
        val call = apiService.getScripts()
        call.enqueue(object : Callback<List<ScriptDTO>> {
            override fun onResponse(call: Call<List<ScriptDTO>>, response: Response<List<ScriptDTO>>) {
                _scripts.value = response.body() ?: emptyList()
            }

            override fun onFailure(call: Call<List<ScriptDTO>>, t: Throwable) {
                _scripts.value = emptyList()
            }
        })
    }
}
