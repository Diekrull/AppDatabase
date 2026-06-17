package appdatabase.config

import appdatabase.data.dto.ErrorResponse
import appdatabase.service.ConflictException
import appdatabase.service.UnauthorizedException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.statuspages.exception
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.respond
import org.jetbrains.exposed.exceptions.ExposedSQLException

// Maneja errores de forma centralizada para que las rutas no repitan try/catch.
fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = cause.message ?: "Solicitud invalida"
                )
            )
        }

        exception<UnauthorizedException> { call, cause ->
            call.respond(
                HttpStatusCode.Unauthorized,
                ErrorResponse(
                    error = cause.message ?: "No autorizado"
                )
            )
        }

        exception<ConflictException> { call, cause ->
            call.respond(
                HttpStatusCode.Conflict,
                ErrorResponse(
                    error = cause.message ?: "Conflicto con el estado actual"
                )
            )
        }

        exception<BadRequestException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "Solicitud invalida",
                    detail = cause.message
                )
            )
        }

        exception<ContentTransformationException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "JSON invalido o incompleto",
                    detail = cause.message
                )
            )
        }

        exception<ExposedSQLException> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "Error de base de datos",
                    detail = cause.cause?.message ?: cause.message
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "Error interno del servidor",
                    detail = cause.message
                )
            )
        }
    }
}
