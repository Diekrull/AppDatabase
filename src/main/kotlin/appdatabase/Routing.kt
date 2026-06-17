package appdatabase

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    // Ruta simple de salud para comprobar que el backend esta levantado.
    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}
