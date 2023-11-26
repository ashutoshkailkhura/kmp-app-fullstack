//package org.example.project.routes
//
//import io.ktor.http.HttpStatusCode
//import io.ktor.server.application.call
//import io.ktor.server.request.receive
//import io.ktor.server.response.respond
//import io.ktor.server.response.respondText
//import io.ktor.server.routing.*
//import entity.Animal
//import kotlinx.coroutines.delay
//import org.example.project.dao.dao
//
//fun Route.petRoute() {
//    route("/pet") {
//        post {
//            val pet = call.receive<Pet>()
//
//            // Validate mandatory fields
//            if (pet.userId.isNullOrBlank() || pet.petName.isNullOrBlank() || pet.breed.isNullOrBlank() || pet.description.isNullOrBlank()) {
//                call.respond(HttpStatusCode.BadRequest, "Mandatory fields are missing.")
//            } else {
//                // Add pet to the database (you need to implement this logic)
//                // Ensure the petId is generated uniquely (e.g., auto-increment in the database)
//                // Respond with the newly created pet
//                call.respond(HttpStatusCode.Created, pet)
//            }
//        }
//
//        put("/{petId}") {
//            val petId = call.parameters["petId"]?.toIntOrNull()
//            if (petId == null) {
//                call.respond(HttpStatusCode.BadRequest, "Invalid pet ID")
//                return@put
//            }
//
//            // Retrieve existing pet from the database based on petId
//            // Update pet fields as needed
//            // Respond with the updated pet
//            val updatedPet = getPetById(petId) // You need to implement this function
//            call.respond(HttpStatusCode.OK, updatedPet)
//        }
//
//        delete("/{petId}") {
//            val petId = call.parameters["petId"]?.toIntOrNull()
//            if (petId == null) {
//                call.respond(HttpStatusCode.BadRequest, "Invalid pet ID")
//                return@delete
//            }
//
//            // Delete pet from the database based on petId
//            // Respond with a success message
//            call.respond(HttpStatusCode.OK, "Pet deleted successfully")
//        }
//    }
//}
////fun Route.animalRoute() {
////
////    route("/animal") {
////
////        get {
////            delay(1_000)
////            call.respond(dao.allAnimals())
////        }
////
////        get("{id?}") {
////            val id = call.parameters["id"] ?: return@get call.respondText(
////                "Missing id",
////                status = HttpStatusCode.BadRequest
////            )
////            val animal =
////                dao.animal(id.toInt()) ?: return@get call.respondText(
////                    "No animal with id $id",
////                    status = HttpStatusCode.NotFound
////                )
////            call.respond(animal)
////        }
////
////        post {
////            val animal = call.receive<Animal>()
////            val newAnimal = dao.addNewAnimal(animal.type, animal.name, animal.story)
////            newAnimal?.let {
////                call.respondText("${it.name} added successfully", status = HttpStatusCode.Created)
////            }
////        }
////
////        delete("{id?}") {
////            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
////            if (dao.deleteAnimal(id.toInt())) {
////                call.respondText("animal removed correctly", status = HttpStatusCode.Accepted)
////            } else {
////                call.respondText("Not Found", status = HttpStatusCode.NotFound)
////            }
////        }
////
////    }
////}