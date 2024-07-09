package com.example.mobileapplication.dto

class RegisterDTO {
    var mail: String? = null
    var password: String? = null
    var passwordCheck: String? = null
    var lastName: String? = null
    var firstName: String? = null


    override fun toString(): String {
        return "RegisterDTO{" +
                "mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", passwordCheck='" + passwordCheck + '\'' +
                '}'
    }
}

