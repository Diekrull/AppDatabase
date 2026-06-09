package com.appdatabase.routes

import appdatabase.config.JwtConfig
import com.appdatabase.data.database.UsersTable
import com.appdatabase.data.dto.AuthResponse
import com.appdatabase.data.dto.LoginRequest
import com.appdatabase.data.dto.RegisterRequest
import com.appdatabase.data.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val repository = UserRepository()

    route("/v1/auth") {

        post("/register") {
            val request = call.receive<RegisterRequest>()

            val userId = repository.createUser(
                username = request.username,
                password = request.password,
                role = request.role
            )

            val token = JwtConfig.generateToken(
                userId = userId.toString(),
                role = request.role
            )

            call.respond(
                HttpStatusCode.Created,
                AuthResponse(
                    token = token,
                    userId = userId.toString(),
                    role = request.role
                )
            )
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val user = repository.findByUsername(request.username)

            if (user == null) {
                call.respond(HttpStatusCode.Unauthorized, "Usuario no encontrado")
                return@post
            }

            val storedPassword = user[UsersTable.password]

            if (storedPassword != request.password) {
                call.respond(HttpStatusCode.Unauthorized, "Contraseña incorrecta")
                return@post
            }

            val userId = user[UsersTable.id].toString()
            val role = user[UsersTable.role]

            val token = JwtConfig.generateToken(userId, role)

            call.respond(
                AuthResponse(
                    token = token,
                    userId = userId,
                    role = role
                )
            )
        }
    }
}