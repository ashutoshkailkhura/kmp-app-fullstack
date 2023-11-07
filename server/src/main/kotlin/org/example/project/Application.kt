package org.example.project

import SERVER_PORT
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.example.project.plugin.configureRouting
import org.example.project.plugin.configureSerialization

fun main() {
    embeddedServer(
        Netty, port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    configureSerialization()
}
