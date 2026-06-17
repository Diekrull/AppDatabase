package appdatabase.domain.model

import java.util.UUID

// Modelo de dominio usado dentro del backend. No se expone directo por HTTP.
data class User(
    val id: UUID,
    val username: String,
    val role: String,
    val passwordHash: String
)
