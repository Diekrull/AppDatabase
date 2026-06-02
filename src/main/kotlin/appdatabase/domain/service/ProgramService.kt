package com.appdatabase.domain.service

import com.appdatabase.data.dto.ProgramDto
import com.appdatabase.data.repository.ProgramRepository
import java.util.UUID

class ProgramService(
    private val programRepository: ProgramRepository = ProgramRepository()
) {

    fun createProgram(userId: String, dto: ProgramDto): String {
        if (dto.name.isBlank()) {
            throw IllegalArgumentException("El nombre del programa no puede estar vacío")
        }

        if (dto.goal.isBlank()) {
            throw IllegalArgumentException("El objetivo del programa no puede estar vacío")
        }

        if (dto.durationWeeks <= 0) {
            throw IllegalArgumentException("La duración debe ser mayor a 0 semanas")
        }

        val programId = programRepository.createProgram(
            userId = UUID.fromString(userId),
            dto = dto
        )

        return programId.toString()
    }

    fun getProgramsByUser(userId: String): List<ProgramDto> {
        return programRepository.getProgramsByUser(
            userId = UUID.fromString(userId)
        )
    }
}