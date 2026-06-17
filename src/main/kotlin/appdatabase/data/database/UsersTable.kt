package appdatabase.data.database

import org.jetbrains.exposed.dao.id.UUIDTable

object UsersTable : UUIDTable("users") {
    val username = varchar("username", 100)
    val passwordHash = varchar("password", 255)
    val role = varchar("role", 50)
}
