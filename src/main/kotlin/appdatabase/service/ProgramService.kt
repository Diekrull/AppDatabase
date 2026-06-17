package appdatabase.service

import appdatabase.data.dto.CreateProgramRequest
import appdatabase.data.repository.ProgramRepository
import appdatabase.domain.model.Program
import java.util.UUID

class ProgramService(
    private val programRepository: ProgramRepository = ProgramRepository()
) {

    fun createProgram(
        userId: UUID,
        request: CreateProgramRequest
    ): Program {
        if (request.name.isBlank()) {
            throw IllegalArgumentException("Nombre vacio")
        }

        if (request.goal.isBlank()) {
            throw IllegalArgumentException("Objetivo vacio")
        }

        if (request.durationWeeks <= 0) {
            throw IllegalArgumentException("La duracion debe ser mayor a 0")
        }

        return programRepository.createProgram(
            userId = userId,
            name = request.name,
            goal = request.goal,
            durationWeeks = request.durationWeeks
        )
    }

    fun getProgramsByUser(userId: UUID): List<Program> =
        programRepository.getProgramsByUser(userId)
}
