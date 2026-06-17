package appdatabase.domain.repository

import appdatabase.domain.model.Program
import java.util.UUID

// Contrato que ProgramService necesita para trabajar con programas.
interface ProgramRepositoryContract {
    fun createProgram(
        userId: UUID,
        name: String,
        goal: String,
        durationWeeks: Int
    ): Program

    fun getProgramsByUser(userId: UUID): List<Program>
}
