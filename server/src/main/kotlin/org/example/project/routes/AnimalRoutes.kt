package org.example.project.routes

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.*
import entity.Animals
import kotlinx.coroutines.delay

fun Route.animalRouting() {
    val animalStorage = mutableListOf<Animals>()

    route("/animal") {
        get {
            delay(2_000)
            call.respond(animalStorage)
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val customer =
                animalStorage.find { it.id == id } ?: return@get call.respondText(
                    "No animal with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }
        post {
            val animal = call.receive<Animals>()
            animalStorage.add(animal)
            call.respondText("Animal added successfully", status = HttpStatusCode.Created)

        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (animalStorage.removeIf { it.id == id }) {
                call.respondText("animal removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}