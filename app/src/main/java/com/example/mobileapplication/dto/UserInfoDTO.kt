package com.example.mobileapplication.dto

data class UserInfoDto(
    val userId: Long,
    val mail: String,
    val nbFollowers: Int,
    val nbFollowing: Int,
    val nbPosts: Int,
    val lastName: String,
    val firstName: String,
    val token: String? = null
)
