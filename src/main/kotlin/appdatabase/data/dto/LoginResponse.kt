package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Respuesta de login: token JWT y datos basicos para el cliente.
@Serializable
data class LoginResponse(
    val token: String,
    val userId: String,
    val role: String
)
