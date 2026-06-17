package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Datos que el cliente envia para crear un programa.
@Serializable
data class CreateProgramRequest(
    val name: String,
    val goal: String,
    val durationWeeks: Int
)
