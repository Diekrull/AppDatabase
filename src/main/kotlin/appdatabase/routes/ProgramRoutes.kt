package appdatabase.routes

import appdatabase.data.dto.CreateProgramRequest
import appdatabase.data.dto.ErrorResponse
import appdatabase.data.mapper.toDto
import appdatabase.service.ProgramService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

// Capa Routes: endpoints protegidos por JWT para crear/listar programas del usuario.
fun Route.programRoutes(programService: ProgramService) {

    authenticate("auth-jwt") {
        route("/v1/programs") {

            post {
                val userId =
                    call.userIdFromToken()
                        ?: return@post call.respond(
                            HttpStatusCode.Unauthorized,
                            ErrorResponse(error = "Token invalido o expirado")
                        )

                val request =
                    call.receive<CreateProgramRequest>()

                val program =
                    programService.createProgram(
                        userId = userId,
                        name = request.name,
                        goal = request.goal,
                        durationWeeks = request.durationWeeks
                    )

                call.respond(
                    HttpStatusCode.Created,
                    program.toDto()
                )
            }

            get {
                val userId =
                    call.userIdFromToken()
                        ?: return@get call.respond(
                            HttpStatusCode.Unauthorized,
                            ErrorResponse(error = "Token invalido o expirado")
                        )

                val programs =
                    programService
                        .getProgramsByUser(userId)
                        .map { it.toDto() }

                call.respond(programs)
            }
        }
    }
}

private fun RoutingCall.userIdFromToken(): UUID? {
    // Extrae el userId que JwtConfig guardo como claim en el token.
    val userId =
        principal<JWTPrincipal>()
            ?.payload
            ?.getClaim("userId")
            ?.asString()

    return userId?.let { runCatching { UUID.fromString(it) }.getOrNull() }
}
