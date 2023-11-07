package org.example.project.plugin

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.example.project.routes.animalRouting

fun Application.configureRouting() {
    routing {
        animalRouting()
    }
}