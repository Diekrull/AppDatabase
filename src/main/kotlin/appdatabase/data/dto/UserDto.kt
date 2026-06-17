package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Usuario expuesto por HTTP. No incluye passwordHash.
@Serializable
data class UserDto(
    val id: String,
    val username: String,
    val role: String
)
