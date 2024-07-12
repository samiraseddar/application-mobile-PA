package com.example.mobileapplication.dto

data class MessageDTO(
    val id: Long,
    val senderId: Long,
    val receiverId: Long,
    val msg: String,
    val timestamp: String
)
