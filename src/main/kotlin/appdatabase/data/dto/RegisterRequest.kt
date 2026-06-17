package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Datos que el cliente envia para crear una cuenta.
@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val role: String = "USER"
)
