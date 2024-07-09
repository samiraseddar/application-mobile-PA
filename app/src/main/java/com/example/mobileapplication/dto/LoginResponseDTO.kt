package com.example.mobileapplication.dto

class LoginResponseDTO {
    // la reponse si le login c'est bien passer
    var userId: Long = 0

    var status: String

    var token: String? = null

    var nbFollowers: Int = 0

    var nbFollowing: Int = 0

    var lastName: String? = null
    var firstName: String? = null

    var nbPosts: Int = 0


    constructor(status: String) {
        this.status = status
    }

    constructor(
        userId: Long,
        status: String,
        token: String?,
        nbFollowers: Int,
        nbFollowing: Int,
        nbPosts: Int,
        firstName: String?,
        lastName: String?
    ) {
        this.userId = userId
        this.status = status
        this.token = token
        this.nbFollowers = nbFollowers
        this.nbFollowing = nbFollowing
        this.nbPosts = nbPosts
        this.lastName = lastName
        this.firstName = firstName
    }
}