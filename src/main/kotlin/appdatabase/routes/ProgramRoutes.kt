package appdatabase.routes

import appdatabase.data.dto.CreateProgramRequest
import appdatabase.data.dto.toDto
import appdatabase.service.ProgramService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.programRoutes() {

    val programService =
        ProgramService()

    authenticate("auth-jwt") {
        route("/v1/programs") {

            post {
                val userId =
                    call.userIdFromToken()
                        ?: return@post call.respond(HttpStatusCode.Unauthorized)

                val request =
                    call.receive<CreateProgramRequest>()

                try {
                    val program =
                        programService.createProgram(
                            userId = userId,
                            request = request
                        )

                    call.respond(
                        HttpStatusCode.Created,
                        program.toDto()
                    )
                } catch (e: IllegalArgumentException) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to e.message)
                    )
                }
            }

            get {
                val userId =
                    call.userIdFromToken()
                        ?: return@get call.respond(HttpStatusCode.Unauthorized)

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
    val userId =
        principal<JWTPrincipal>()
            ?.payload
            ?.getClaim("userId")
            ?.asString()

    return userId?.let { UUID.fromString(it) }
}
