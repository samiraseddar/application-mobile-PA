package com.example.mobileapplication.dto.script

data class ScriptCreateDTO(
    val name: String,
    val protectionLevel: String = "PRIVATE",
    val language: String,
    val inputFiles: String = "",
    val outputFiles: String = ""
) 