package appdatabase.routes

import appdatabase.data.dto.AuthResponse
import appdatabase.data.dto.LoginRequest
import appdatabase.data.dto.RegisterRequest
import appdatabase.data.dto.toDto
import appdatabase.service.AuthService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {

    val authService = AuthService()

    route("/v1/auth") {

        post("/register") {
            val request =
                call.receive<RegisterRequest>()

            try {
                val user =
                    authService.register(request)

                call.respond(
                    HttpStatusCode.Created,
                    user.toDto()
                )
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to e.message)
                )
            }
        }

        post("/login") {
            val request =
                call.receive<LoginRequest>()

            try {
                val user =
                    authService.login(request)

                val token =
                    authService.generateToken(user)

                call.respond(
                    AuthResponse(
                        token = token,
                        userId = user.id.toString(),
                        role = user.role
                    )
                )
            } catch (e: IllegalArgumentException) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf("error" to e.message)
                )
            }
        }
    }
}
