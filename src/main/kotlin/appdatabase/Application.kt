package appdatabase


import appdatabase.config.configureDatabase
import appdatabase.config.configureAuthentication
import appdatabase.config.configureErrorHandling
import appdatabase.data.dto.ErrorResponse
import appdatabase.data.repository.ProgramRepository
import appdatabase.data.repository.UserRepository
import appdatabase.routes.authRoutes
import appdatabase.routes.programRoutes
import appdatabase.service.AuthService
import appdatabase.service.JwtTokenService
import appdatabase.service.ProgramService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import kotlinx.serialization.json.Json

fun main() {
    // Punto de entrada: levanta Ktor embebido en Netty.
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    // Orden de arranque: plugins HTTP, errores, base de datos, seguridad y rutas.
    configureSerialization()
    configureErrorHandling()
    configureDatabase()
    configureAuthentication()

    configureRouting()

    // Composition root: aqui se conectan implementaciones concretas con servicios.
    val authService =
        AuthService(
            userRepository = UserRepository(),
            tokenService = JwtTokenService()
        )

    val programService =
        ProgramService(
            programRepository = ProgramRepository()
        )

    routing {
        authRoutes(authService)
        programRoutes(programService)

        // Fallback para cualquier ruta no registrada.
        route("{...}") {
            handle {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(error = "Ruta no encontrada")
                )
            }
        }
    }

    println("Backend iniciado en http://localhost:8080")
}


fun Application.configureSerialization() {
    // Habilita recibir y responder JSON con DTOs serializables.
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
}
