package com.appdatabase.data.repository


import com.appdatabase.data.database.ProgramsTable
import com.appdatabase.data.dto.ProgramDto
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class ProgramRepository {

    fun createProgram(userId: UUID, dto: ProgramDto): UUID {
        return transaction {
            ProgramsTable.insert {
                it[ProgramsTable.userId] = userId
                it[name] = dto.name
                it[goal] = dto.goal
                it[durationWeeks] = dto.durationWeeks
            }[ProgramsTable.id]
        }
    }

    fun getProgramsByUser(userId: UUID): List<ProgramDto> {
        return transaction {
            ProgramsTable
                .selectAll().where { ProgramsTable.userId eq userId }
                .map {
                    ProgramDto(
                        id = it[ProgramsTable.id].toString(),
                        name = it[ProgramsTable.name],
                        goal = it[ProgramsTable.goal],
                        durationWeeks = it[ProgramsTable.durationWeeks]
                    )
                }
        }
    }
}