package com.appdatabase.data.dto

import kotlinx.serialization.Serializable

@Serializable

data class AuthResponse(
    val token: String,
    val userId: String,
    val role: String
)