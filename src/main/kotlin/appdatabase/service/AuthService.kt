package appdatabase.service

import appdatabase.domain.model.User
import appdatabase.domain.repository.UserRepositoryContract
import appdatabase.security.PasswordHasher

// Capa Service: valida reglas de auth y coordina repositorio + password hashing + JWT.
class AuthService(
    private val userRepository: UserRepositoryContract,
    private val tokenService: TokenService
) {

    fun register(
        username: String,
        password: String,
        role: String
    ): User {
        // Las reglas de negocio viven en Service, no en DTO ni Repository.
        validateCredentials(
            username = username,
            password = password
        )

        val existingUser =
            userRepository.findByUsername(username)

        if (existingUser != null) {
            throw ConflictException("El usuario ya existe")
        }

        val passwordHash =
            PasswordHasher.hashPassword(password)

        // El repositorio solo recibe el hash, nunca la password plana.
        return userRepository.createUser(
            username = username,
            passwordHash = passwordHash,
            role = role
        )
    }

    fun login(
        username: String,
        password: String
    ): User {
        validateCredentials(
            username = username,
            password = password
        )

        val user =
            userRepository.findByUsername(username)
                ?: throw UnauthorizedException("Usuario no encontrado")

        // Compara la password recibida contra el hash guardado en DB.
        val validPassword =
            PasswordHasher.verifyPassword(
                password,
                user.passwordHash
            )

        if (!validPassword) {
            throw UnauthorizedException("Contrasena incorrecta")
        }

        return user
    }

    fun generateToken(user: User): String =
        // El token se genera despues de autenticar correctamente.
        tokenService.generateToken(user)

    private fun validateCredentials(
        username: String,
        password: String
    ) {
        if (username.isBlank()) {
            throw IllegalArgumentException("Username vacio")
        }

        if (password.length < 8) {
            throw IllegalArgumentException("La contrasena debe tener al menos 8 caracteres")
        }
    }
}
