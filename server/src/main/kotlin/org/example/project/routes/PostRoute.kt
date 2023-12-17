//package org.example.project.routes
//
//import entity.Post
//import io.ktor.http.*
//import io.ktor.server.application.*
//import io.ktor.server.request.*
//import io.ktor.server.response.*
//import io.ktor.server.routing.*
//
//fun Route.postRoute() {
//    route("/post") {
//        post {
//            val post = call.receive<Post>()
//
//            // Validate mandatory fields
//            if (post.userId.isNullOrBlank() || post.location.isNullOrBlank() || post.postDetails.isNullOrBlank()) {
//                call.respond(HttpStatusCode.BadRequest, "Mandatory fields are missing.")
//            } else {
//                // Add post to the database (you need to implement this logic)
//                // Ensure the postId is generated uniquely (e.g., auto-increment in the database)
//                // Respond with the newly created post
//                call.respond(HttpStatusCode.Created, post)
//            }
//        }
//
//        put("/{postId}") {
//            val postId = call.parameters["postId"]?.toIntOrNull()
//            if (postId == null) {
//                call.respond(HttpStatusCode.BadRequest, "Invalid post ID")
//                return@put
//            }
//
//            // Retrieve existing post from the database based on postId
//            // Update post fields as needed
//            // Respond with the updated post
//            val updatedPost = getPostById(postId) // You need to implement this function
//            call.respond(HttpStatusCode.OK, updatedPost)
//        }
//
//        delete("/{postId}") {
//            val postId = call.parameters["postId"]?.toIntOrNull()
//            if (postId == null) {
//                call.respond(HttpStatusCode.BadRequest, "Invalid post ID")
//                return@delete
//            }
//
//            // Delete post from the database based on postId
//            // Respond with a success message
//            call.respond(HttpStatusCode.OK, "Post deleted successfully")
//        }
//    }
//}