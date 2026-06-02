package com.appdatabase.config

import com.appdatabase.data.database.ProgramsTable
import com.appdatabase.data.database.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/project_db",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "tu_password"
    )

    transaction {
        SchemaUtils.create(UsersTable, ProgramsTable)
    }
}