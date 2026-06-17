package appdatabase


import appdatabase.config.configureDatabase
import appdatabase.config.configureAuthentication
import appdatabase.routes.authRoutes
import appdatabase.routes.programRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureDatabase()
    configureAuthentication()

    configureRouting()

    routing {
        authRoutes()
        programRoutes()
    }

    println("Backend iniciado en http://localhost:8080")
}


fun Application.configureSerialization() {
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
