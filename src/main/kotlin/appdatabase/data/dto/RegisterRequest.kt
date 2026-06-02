package com.appdatabase.data.dto

import kotlinx.serialization.Serializable

@Serializable

data class RegisterRequest(
    val username: String,
    val password: String,
    val token: String,
    val userId: String,
    val role: String
)