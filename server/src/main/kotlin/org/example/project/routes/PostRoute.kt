package org.example.project.routes

import org.example.project.data.request.PostRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.dao.DAOPostImpl

fun Route.postRoute(postDao: DAOPostImpl) {

    authenticate {

        get("post") {
            println("XXX : getting all post ")
            val postList = postDao.getAllPost()
            call.respond(HttpStatusCode.OK, postList)
        }

        get("post/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            userId?.let {
                val postList = postDao.getPostOfUser(userId)
                call.respond(HttpStatusCode.OK, postList)
            }

        }

//        get("post/{postId}") {
//            val postId = call.parameters["postId"]?.toIntOrNull()
//            postId?.let {
//                val postDetail = postDao.getPostDetail(postId)
//                call.respond(HttpStatusCode.OK, postDetail)
//            }
//        }

        post("post") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class)
            println("XXX $userId")
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict, "userId is Null")
                return@post
            }

            val post = call.receive<PostRequest>()
            if (post.content.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Empty Content")
            }

            val postRes = postDao.createPost(userId, post)
            if (postRes == null) {
                call.respond(HttpStatusCode.NoContent, "Post Not Created")
                return@post
            }

            call.respond(HttpStatusCode.OK, "Created")
        }

//        delete("post/{postId}/delete") {
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

    }
}