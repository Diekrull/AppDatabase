package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Credenciales que el cliente envia para iniciar sesion.
@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
