package appdatabase.data.mapper

import appdatabase.data.dto.ProgramDto
import appdatabase.domain.model.Program

// Mapper: convierte modelos internos de dominio a DTOs de salida HTTP.
fun Program.toDto(): ProgramDto =
    ProgramDto(
        id = id.toString(),
        userId = userId.toString(),
        name = name,
        goal = goal,
        durationWeeks = durationWeeks
    )
