package appdatabase.data.database

import org.jetbrains.exposed.dao.id.UUIDTable

// Tabla fisica users. Solo define columnas; no contiene reglas de negocio.
object UsersTable : UUIDTable("users") {
    val username = varchar("username", 100)

    // La columna real se llama password, pero almacena el hash BCrypt.
    val passwordHash = varchar("password", 255)
    val role = varchar("role", 50)
}
