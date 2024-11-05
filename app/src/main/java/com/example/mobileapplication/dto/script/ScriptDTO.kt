package com.example.mobileapplication.dto.script


data class ScriptDTO(
    val id: Long,
    val name: String,
    val location: String,
    val protectionLevel: String,
    val language: String,
    val inputFileExtensions: String,
    val outputFileNames: String,
    val userId: Long,
    val nbLikes: Int,
    val nbDislikes: Int
)
