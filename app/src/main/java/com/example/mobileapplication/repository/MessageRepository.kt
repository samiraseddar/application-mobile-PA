package com.example.mobileapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MessageRepository(private val apiService: ApiService) {

    private val _messageResponse = MutableLiveData<MessageDTO?>()
    val messageResponse: LiveData<MessageDTO?> get() = _messageResponse

    private val _messagesBetweenUsers = MutableLiveData<Set<MessageDTO>>()
    val messagesBetweenUsers: LiveData<Set<MessageDTO>> get() = _messagesBetweenUsers

    fun sendMessage(receiverId: Long, message: MessageDTO) {
        val call = apiService.sendMessage(receiverId, message)
        call.enqueue(object : Callback<MessageDTO> {
            override fun onResponse(call: Call<MessageDTO>, response: Response<MessageDTO>) {
                _messageResponse.value = response.body()
            }

            override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                _messageResponse.value = null
            }
        })
    }

    fun updateMessage(id: Long, newMessage: String) {
        val call = apiService.updateMessage(id, newMessage)
        call.enqueue(object : Callback<MessageDTO> {
            override fun onResponse(call: Call<MessageDTO>, response: Response<MessageDTO>) {
                _messageResponse.value = response.body()
            }

            override fun onFailure(call: Call<MessageDTO>, t: Throwable) {
                _messageResponse.value = null
            }
        })
    }

    fun deleteMessage(senderId: Long, messageId: Long) {
        val call = apiService.deleteMessage(senderId, messageId)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {}

            override fun onFailure(call: Call<Void>, t: Throwable) {}
        })
    }

    fun getMessagesBetweenUsers(userId1: Long, userId2: Long) {
        val call = apiService.getMessagesBetweenUsers(userId1, userId2)
        call.enqueue(object : Callback<Set<MessageDTO>> {
            override fun onResponse(call: Call<Set<MessageDTO>>, response: Response<Set<MessageDTO>>) {
                _messagesBetweenUsers.value = response.body() ?: emptySet()
            }

            override fun onFailure(call: Call<Set<MessageDTO>>, t: Throwable) {
                _messagesBetweenUsers.value = emptySet()
            }
        })
    }

    fun searchMessagesInConversation(userId1: Long, userId2: Long, word: String) {
        val call = apiService.searchMessagesInConversation(userId1, userId2, word)
        call.enqueue(object : Callback<Set<MessageDTO>> {
            override fun onResponse(call: Call<Set<MessageDTO>>, response: Response<Set<MessageDTO>>) {
                _messagesBetweenUsers.value = response.body() ?: emptySet()
            }

            override fun onFailure(call: Call<Set<MessageDTO>>, t: Throwable) {
                _messagesBetweenUsers.value = emptySet()
            }
        })
    }
}
