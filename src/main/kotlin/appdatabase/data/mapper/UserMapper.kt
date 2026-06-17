package appdatabase.data.mapper

import appdatabase.data.dto.UserDto
import appdatabase.domain.model.User

// Mapper: convierte el modelo interno a respuesta segura para el cliente.
fun User.toDto(): UserDto =
    UserDto(
        id = id.toString(),
        username = username,
        role = role
    )
