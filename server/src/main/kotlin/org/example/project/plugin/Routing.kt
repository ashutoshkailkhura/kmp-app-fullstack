package org.example.project.plugin

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import org.example.project.dao.*
import org.example.project.routes.*
import org.example.project.routes.websocket.chatRoute
import org.example.project.security.TokenConfig
import org.example.project.security.TokenService
import org.example.project.security.hasing.HashingService
import javax.naming.AuthenticationException

fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    val userDao: DAOUser = DAOUserImpl()
    val userProfileDao = DAOUserProfileImpl()
    val postDao = DAOPostImpl()
    val chatMessageDao = DAOChatMessageImpl()
    val chatConversationDao = DAOChatConversationImpl()

    routing {
        authentication(userDao, hashingService, tokenService, tokenConfig)
        userProfile(userProfileDao)
        postRoute(postDao)
        chatRoute(chatMessageDao, chatConversationDao)
    }
}

fun ApplicationCall.getUserIdFromPrincipal(): Int {
    return principal<JWTPrincipal>()?.getClaim("userId", Int::class) ?: throw AuthenticationException("Invalid user")
}