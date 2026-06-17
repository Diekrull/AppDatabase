package appdatabase.data.dto

import appdatabase.domain.model.Program
import kotlinx.serialization.Serializable

@Serializable
data class ProgramDto(
    val id: String,
    val userId: String,
    val name: String,
    val goal: String,
    val durationWeeks: Int
)

@Serializable
data class CreateProgramRequest(
    val name: String,
    val goal: String,
    val durationWeeks: Int
)

fun Program.toDto(): ProgramDto =
    ProgramDto(
        id = id.toString(),
        userId = userId.toString(),
        name = name,
        goal = goal,
        durationWeeks = durationWeeks
    )
