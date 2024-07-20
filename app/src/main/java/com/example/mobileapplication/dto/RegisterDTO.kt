package com.example.mobileapplication.dto

data class RegisterDTO (
    var mail: String = "test@gmail.com",
    var password: String,
    var passwordCheck: String,
    var lastName: String = "ok",
    var firstName: String = "prenom")
{
    override fun toString(): String {
        return "RegisterDTO{" +
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                '}'
    }
}

