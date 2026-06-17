package appdatabase.data.repository

import appdatabase.data.database.UsersTable
import appdatabase.domain.model.User
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun createUser(
        username: String,
        passwordHash: String,
        role: String
    ): User {
        return transaction {
            val id = UsersTable.insertAndGetId { row ->
                row[UsersTable.username] = username
                row[UsersTable.passwordHash] = passwordHash
                row[UsersTable.role] = role
            }

            User(
                id = id.value,
                username = username,
                role = role,
                passwordHash = passwordHash
            )
        }
    }

    fun findByUsername(username: String): User? {
        return transaction {
            UsersTable
                .selectAll()
                .where { UsersTable.username eq username }
                .map { row ->
                    User(
                        id = row[UsersTable.id].value,
                        username = row[UsersTable.username],
                        role = row[UsersTable.role],
                        passwordHash = row[UsersTable.passwordHash]
                    )
                }
                .singleOrNull()
        }
    }
}
