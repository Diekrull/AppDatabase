package appdatabase.data.dto

import appdatabase.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val username: String,
    val role: String
)

fun User.toDto(): UserDto =
    UserDto(
        id = id.toString(),
        username = username,
        role = role
    )
