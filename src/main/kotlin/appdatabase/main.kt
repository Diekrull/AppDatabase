package com.appdatabase

import com.appdatabase.rootModule
import io.ktor.server.engine.*
import io.ktor.server.application.*
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(
        factory = Netty,
        port = 8080,
        host = "0.0.0.0",
        module = Application::rootModule
    ).start(wait = true)
}
