package com.appdatabase.domain.service

import appdatabase.config.JwtConfig
import com.appdatabase.data.database.UsersTable
import com.appdatabase.data.dto.AuthResponse
import com.appdatabase.data.dto.LoginRequest
import com.appdatabase.data.dto.RegisterRequest
import com.appdatabase.data.repository.UserRepository
import appdatabase.security.PasswordHasher

class AuthService(
    private val userRepository: UserRepository = UserRepository()
) {

    fun register(request: RegisterRequest): AuthResponse {
        val existingUser = userRepository.findByUsername(request.username)

        if (existingUser != null) {
            throw IllegalArgumentException("El usuario ya existe")
        }

        val hashedPassword = PasswordHasher.hashPassword(request.password)

        val userId = userRepository.createUser(
            username = request.username,
            password = hashedPassword,
            role = request.role
        )

        val token = JwtConfig.generateToken(
            userId = userId.toString(),
            role = request.role
        )

        return AuthResponse(
            token = token,
            userId = userId.toString(),
            role = request.role
        )
    }

    fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            ?: throw IllegalArgumentException("Usuario no encontrado")

        val storedHashedPassword = user[UsersTable.password]

        val isPasswordValid = PasswordHasher.verifyPassword(
            password = request.password,
            hashedPassword = storedHashedPassword
        )

        if (!isPasswordValid) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        val userId = user[UsersTable.id].toString()
        val role = user[UsersTable.role]

        val token = JwtConfig.generateToken(
            userId = userId,
            role = role
        )

        return AuthResponse(
            token = token,
            userId = userId,
            role = role
        )
    }
}