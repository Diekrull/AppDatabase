package appdatabase.domain.model

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val role: String,
    val passwordHash: String
)
