package com.appdatabase.routes

import com.appdatabase.data.dto.ProgramDto
import com.appdatabase.data.repository.ProgramRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.UUID

fun Route.programRoutes() {
    val repository = ProgramRepository()

    authenticate("auth-jwt") {
        route("/v1/programs") {

            post {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asString()

                if (userId == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@post
                }

                val request = call.receive<ProgramDto>()

                val programId = repository.createProgram(
                    userId = UUID.fromString(userId),
                    dto = request
                )

                call.respond(
                    HttpStatusCode.Created,
                    mapOf("programId" to programId.toString())
                )
            }

            get {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asString()

                if (userId == null) {
                    call.respond(HttpStatusCode.Unauthorized)
                    return@get
                }

                val programs = repository.getProgramsByUser(
                    UUID.fromString(userId)
                )

                call.respond(programs)
            }
        }
    }
}