package appdatabase.data.repository

import appdatabase.data.database.ProgramsTable
import appdatabase.data.database.UsersTable
import appdatabase.domain.model.Program
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class ProgramRepository {

    fun createProgram(
        userId: UUID,
        name: String,
        goal: String,
        durationWeeks: Int
    ): Program {
        return transaction {
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

    fun getProgramsByUser(userId: UUID): List<Program> {
        return transaction {
            ProgramsTable
                .selectAll()
                .where { ProgramsTable.userId eq EntityID(userId, UsersTable) }
                .map { row ->
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
