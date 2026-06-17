package appdatabase.service

import appdatabase.domain.model.Program
import appdatabase.domain.repository.ProgramRepositoryContract
import java.util.UUID

// Capa Service: valida reglas de programas y delega persistencia al repositorio.
class ProgramService(
    private val programRepository: ProgramRepositoryContract
) {

    fun createProgram(
        userId: UUID,
        name: String,
        goal: String,
        durationWeeks: Int
    ): Program {
        // Las rutas reciben HTTP; las reglas de negocio viven aqui.
        if (name.isBlank()) {
            throw IllegalArgumentException("Nombre vacio")
        }

        if (goal.isBlank()) {
            throw IllegalArgumentException("Objetivo vacio")
        }

        if (durationWeeks <= 0) {
            throw IllegalArgumentException("La duracion debe ser mayor a 0")
        }

        return programRepository.createProgram(
            userId = userId,
            name = name,
            goal = goal,
            durationWeeks = durationWeeks
        )
    }

    fun getProgramsByUser(userId: UUID): List<Program> =
        programRepository.getProgramsByUser(userId)
}
