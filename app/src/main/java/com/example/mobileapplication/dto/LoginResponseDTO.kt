package com.example.mobileapplication.dto

data class LoginResponseDTO (
    // la reponse si le login c'est bien passer
    var userId: Long = 0,

    var status: String,

    var token: String? = null,

    var nbFollowers: Int = 0,

    var nbFollowing: Int = 0,

    var lastName: String? = null,
    var firstName: String? = null,

    var nbPosts: Int = 0
)
