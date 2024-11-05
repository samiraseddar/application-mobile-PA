package com.example.mobileapplication.service

import com.example.mobileapplication.dto.LoginDTO
import com.example.mobileapplication.dto.LoginResponseDTO
import com.example.mobileapplication.dto.MessageDTO
import com.example.mobileapplication.dto.RegisterDTO
import com.example.mobileapplication.dto.RegisterResponseDTO
import com.example.mobileapplication.dto.UserInfoDto
import com.example.mobileapplication.dto.script.ScriptDTO
import com.example.mobileapplication.dto.script.ScriptRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Query

interface ApiService {
    @POST("/api/users/signIn")
    suspend fun login(@Body loginDTO: LoginDTO?): LoginResponseDTO

    @POST("/api/users/signUp")
    suspend fun register(@Body registerDTO: RegisterDTO?): RegisterResponseDTO

    @GET("/api/scripts")
    suspend fun getScripts(@Header("Authorization") authToken: String): Response<List<ScriptRequest>>

    @POST("/api/scripts")
    suspend fun createScript(
        @Header("Authorization") token: String,
        @Body scriptRequest: ScriptRequest
    ): Response<ScriptDTO>
    @POST("/api/messages/send")
    suspend fun sendMessage(
        @Query("senderId") senderId: Long,
        @Query("receiverId") receiverId: Long,
        @Body message: MessageDTO,
        @Header("Authorization") token: String
    ): MessageDTO

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

    @GET("/api/users/search")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Header("Authorization") token: String
    ): List<UserInfoDto>

    @GET("/api/messages/search")
    fun searchMessagesInConversation(
        @Query("userId1") userId1: Long,
        @Query("userId2") userId2: Long,
        @Query("word") word: String
    ): Call<Set<MessageDTO>>

    @GET("/api/users/{userId}")
    fun getUsersInfo(@Path("userId") userId: Long, @Header("Authorization") token: String): Call<UserInfoDto>

    @GET("/api/users/isFollowing/{userId}")
    suspend fun isFollowing(
        @Path("userId") userId: Long,
        @Header("Authorization") token: String
    ): Boolean

    @POST("/api/users/{userId}/follows")
    suspend fun followUser(
        @Path("userId") userId: Long,
        @Header("Authorization") token: String
    ): Response<Void>

    @DELETE("/api/users/{userId}/follows")
    suspend fun unfollowUser(
        @Path("userId") userId: Long,
        @Header("Authorization") token: String
    ): Response<Void>



}
