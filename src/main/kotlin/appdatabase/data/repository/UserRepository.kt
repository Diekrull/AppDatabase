package com.appdatabase.data.repository

import com.appdatabase.data.database.UsersTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID
import org.jetbrains.exposed.sql.selectAll

class UserRepository {

    fun createUser(username: String, password: String, role: String): UUID {
        return transaction {
            UsersTable.insert {
                it[UsersTable.username] = username
                it[UsersTable.password] = password
                it[UsersTable.role] = role
            }[UsersTable.id]
        }
    }

    fun findByUsername(username: String): ResultRow? {
        return transaction {
            UsersTable
                .selectAll().where { UsersTable.username eq username }
                .singleOrNull()
        }
    }
}