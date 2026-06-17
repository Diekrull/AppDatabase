package appdatabase.domain.repository

import appdatabase.domain.model.User

// Contrato que AuthService necesita para trabajar con usuarios.
interface UserRepositoryContract {
    fun createUser(
        username: String,
        passwordHash: String,
        role: String
    ): User

    fun findByUsername(username: String): User?
}
