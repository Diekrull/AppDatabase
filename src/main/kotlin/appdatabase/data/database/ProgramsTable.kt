package com.appdatabase.data.database

import org.jetbrains.exposed.sql.Table

object ProgramsTable : Table("programs") {
    val id = uuid("id").autoGenerate()
    val userId = uuid("user_id").references(UsersTable.id)
    val name = varchar("name", 150)
    val goal = varchar("goal", 150)
    val durationWeeks = integer("duration_weeks")

    override val primaryKey = PrimaryKey(id)
}