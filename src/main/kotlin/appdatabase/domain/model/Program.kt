package appdatabase.domain.model

import java.util.UUID

// Modelo de dominio que representa un programa ya persistido.
data class Program(
    val id: UUID,
    val userId: UUID,
    val name: String,
    val goal: String,
    val durationWeeks: Int
)
