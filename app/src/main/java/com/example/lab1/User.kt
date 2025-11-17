package com.example.lab1

data class User(
    val id: Long = 0,
    val login: String,
    val password: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val isAdmin: Boolean = false,
    val avatarUri: String? = null,
    val theme: String = "light" // light, dark
)