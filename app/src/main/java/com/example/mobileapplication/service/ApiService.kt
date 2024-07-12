package com.example.mobileapplication.service

import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.dto.script.ScriptDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/login")
    fun login(@Body loginDTO: LoginDTO?): Call<LoginResponseDTO?>?

    @POST("/api/register")
    suspend fun register(@Body registerDTO: RegisterDTO?): Call<RegisterResponseDTO?>?

    @GET("/api/scripts")
    fun getScripts(): Call<List<ScriptDTO>>

    @POST("/api/messages/send")
    fun sendMessage(
        @Query("receiverId") receiverId: Long,
        @Body message: MessageDTO
    ): Call<MessageDTO>

    @PUT("/api/messages/update/{id}")
    fun updateMessage(
        @Path("id") id: Long,
        @Body newMessage: String
    ): Call<MessageDTO>

    @DELETE("/api/messages/delete")
    fun deleteMessage(
        @Query("senderId") senderId: Long,
        @Query("messageId") messageId: Long
    ): Call<Void>

    @GET("/api/messages/conversation")
    fun getMessagesBetweenUsers(
        @Query("userId1") userId1: Long,
        @Query("userId2") userId2: Long
    ): Call<Set<MessageDTO>>

    @GET("/api/messages/search")
    fun searchMessagesInConversation(
        @Query("userId1") userId1: Long,
        @Query("userId2") userId2: Long,
        @Query("word") word: String
    ): Call<Set<MessageDTO>>
}