package appdatabase.data.repository

import appdatabase.data.database.ProgramsTable
import appdatabase.data.database.UsersTable
import appdatabase.domain.model.Program
import appdatabase.domain.repository.ProgramRepositoryContract
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

// Capa Repository: encapsula SQL/Exposed para programs.
class ProgramRepository : ProgramRepositoryContract {

    override fun createProgram(
        userId: UUID,
        name: String,
        goal: String,
        durationWeeks: Int
    ): Program {
        return transaction {
            // user_id es una FK, por eso Exposed requiere EntityID.
            val id = ProgramsTable.insertAndGetId { row ->
                row[ProgramsTable.userId] = EntityID(userId, UsersTable)
                row[ProgramsTable.name] = name
                row[ProgramsTable.goal] = goal
                row[ProgramsTable.durationWeeks] = durationWeeks
            }

            Program(
                id = id.value,
                userId = userId,
                name = name,
                goal = goal,
                durationWeeks = durationWeeks
            )
        }
    }

    override fun getProgramsByUser(userId: UUID): List<Program> {
        return transaction {
            ProgramsTable
                .selectAll()
                .where { ProgramsTable.userId eq EntityID(userId, UsersTable) }
                .map { row ->
                    // Mapea cada fila SQL al modelo usado por services/routes.
                    Program(
                        id = row[ProgramsTable.id].value,
                        userId = row[ProgramsTable.userId].value,
                        name = row[ProgramsTable.name],
                        goal = row[ProgramsTable.goal],
                        durationWeeks = row[ProgramsTable.durationWeeks]
                    )
                }
        }
    }
}
