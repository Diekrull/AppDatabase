package com.appdatabase

import com.appdatabase.config.configureDatabase
import com.appdatabase.routes.authRoutes
import com.appdatabase.routes.programRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabase()

    routing {
        authRoutes()
        programRoutes()
    }
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