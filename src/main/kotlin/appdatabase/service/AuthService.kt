package appdatabase.service

import appdatabase.config.JwtConfig
import appdatabase.data.dto.LoginRequest
import appdatabase.data.dto.RegisterRequest
import appdatabase.data.repository.UserRepository
import appdatabase.domain.model.User
import appdatabase.security.PasswordHasher

class AuthService(
    private val userRepository: UserRepository = UserRepository()
) {

    fun register(request: RegisterRequest): User {
        if (request.username.isBlank()) {
            throw IllegalArgumentException("Username vacio")
        }

        if (request.password.length < 8) {
            throw IllegalArgumentException("La contrasena debe tener al menos 8 caracteres")
        }

        val existingUser =
            userRepository.findByUsername(request.username)

        if (existingUser != null) {
            throw IllegalArgumentException("El usuario ya existe")
        }

        val passwordHash =
            PasswordHasher.hashPassword(request.password)

        return userRepository.createUser(
            username = request.username,
            passwordHash = passwordHash,
            role = request.role
        )
    }

    fun login(request: LoginRequest): User {
        val user =
            userRepository.findByUsername(request.username)
                ?: throw IllegalArgumentException("Usuario no encontrado")

        val validPassword =
            PasswordHasher.verifyPassword(
                request.password,
                user.passwordHash
            )

        if (!validPassword) {
            throw IllegalArgumentException("Contrasena incorrecta")
        }

        return user
    }

    fun generateToken(user: User): String =
        JwtConfig.generateToken(
            user.id.toString(),
            user.role
        )
}
