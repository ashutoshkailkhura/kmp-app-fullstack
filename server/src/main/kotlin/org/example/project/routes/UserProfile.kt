package org.example.project.routes

import org.example.project.data.request.ProfileUpdateRequest
import org.example.project.entity.User
import org.example.project.entity.UserProfile
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.dao.DAOUserProfile

fun Route.userProfile(
    userProfileDao: DAOUserProfile
) {
    authenticate {
        get("profile") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class)
            println("XXX $userId")
            if (userId == null) {
                call.respond(HttpStatusCode.Conflict, "userId is Null")
                return@get
            }

            val userProfileDetail = userProfileDao.getUserProfile(userId)

            if (userProfileDetail == null) {
                call.respond(HttpStatusCode.NoContent, "Profile Not Found")
                return@get
            }

            call.respond(HttpStatusCode.OK, userProfileDetail)
        }

        post("profile") {
            val request = call.receiveOrNull<ProfileUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, "Request Body Incorrect")
                return@post
            }
            val areFieldsBlank = request.name.isBlank() || request.state.isBlank()
            if (areFieldsBlank) {
                call.respond(HttpStatusCode.Conflict, "Check Field")
                return@post
            }

            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", Int::class)

            if (userId == null) {
                call.respond(HttpStatusCode.Conflict, "userId is Null")
                return@post
            }

            val userInfo = UserProfile(
                name = request.name,
                state = request.state
            )
            val wasAcknowledged = userProfileDao.updateUserProfile(userId, userInfo)
            println("XXX $wasAcknowledged")
            if (wasAcknowledged == null) {
                call.respond(HttpStatusCode.Conflict)
                return@post
            }

            call.respond(HttpStatusCode.OK, "Updated")
        }
    }
}