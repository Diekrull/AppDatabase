package appdatabase.routes

import appdatabase.data.dto.LoginRequest
import appdatabase.data.dto.LoginResponse
import appdatabase.data.dto.RegisterRequest
import appdatabase.data.mapper.toDto
import appdatabase.service.AuthService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

// Capa Routes: expone endpoints HTTP y delega reglas a AuthService.
fun Route.authRoutes(authService: AuthService) {

    route("/v1/auth") {

        post("/register") {
            val request =
                call.receive<RegisterRequest>()

            val user =
                authService.register(
                    username = request.username,
                    password = request.password,
                    role = request.role
                )

            call.respond(
                HttpStatusCode.Created,
                user.toDto()
            )
        }

        post("/login") {
            val request =
                call.receive<LoginRequest>()

            val user =
                authService.login(
                    username = request.username,
                    password = request.password
                )

            val token =
                authService.generateToken(user)

            call.respond(
                LoginResponse(
                    token = token,
                    userId = user.id.toString(),
                    role = user.role
                )
            )
        }
    }
}
