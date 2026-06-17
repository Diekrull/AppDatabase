package appdatabase.data.dto

import kotlinx.serialization.Serializable

// Respuesta JSON unica para errores HTTP del backend.
@Serializable
data class ErrorResponse(
    val error: String,
    val detail: String? = null
)
