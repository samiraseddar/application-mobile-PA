package com.example.mobileapplication.dto.script

data class ScriptResponseDTO(
    val id: Long,
    val name: String,
    val protectionLevel: String,
    val language: String,
    val userId: Long,
    val nbLikes: Int,
    val nbDislikes: Int
) 