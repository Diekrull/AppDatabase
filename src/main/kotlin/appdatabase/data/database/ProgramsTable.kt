package appdatabase.data.database

import org.jetbrains.exposed.dao.id.UUIDTable

object ProgramsTable : UUIDTable("programs") {
    val userId = reference("user_id", UsersTable)
    val name = varchar("name", 150)
    val goal = varchar("goal", 150)
    val durationWeeks = integer("duration_weeks")
}
