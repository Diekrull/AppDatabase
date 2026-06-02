package com.appdatabase.data.database

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = uuid("id").autoGenerate()
    val username = varchar("username", 100).uniqueIndex()
    val password = varchar("password", 255)
    val role = varchar("role", 50)

    override val primaryKey = PrimaryKey(id)
}