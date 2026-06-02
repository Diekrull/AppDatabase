package com.appdatabase.domain.service

import com.appdatabase.config.JwtConfig
import com.appdatabase.data.dto.AuthResponse
import com.appdatabase.data.dto.LoginRequest
import com.appdatabase.data.dto.RegisterRequest
import com.appdatabase.data.repository.UserRepository
import com.appdatabase.data.database.UsersTable


class AuthService(
    private val userRepository: UserRepository = UserRepository()
) {

    fun register(request: RegisterRequest): AuthResponse {
        val existingUser = userRepository.findByUsername(request.username)

        if (existingUser != null) {
            throw IllegalArgumentException("El usuario ya existe")
        }

        val userId = userRepository.createUser(
            username = request.username,
            password = request.password,
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

        val storedPassword = user[UsersTable.password]

        if (storedPassword != request.password) {
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