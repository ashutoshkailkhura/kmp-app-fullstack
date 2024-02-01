package org.example.project.plugin

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.example.project.dao.DAOPostImpl
import org.example.project.dao.DAOUser
import org.example.project.dao.DAOUserImpl
import org.example.project.dao.DAOUserProfileImpl
import org.example.project.routes.authRoute
import org.example.project.routes.postRoute
import org.example.project.routes.profileRoute
import org.example.project.routes.websocket.chatRoute
import org.example.project.security.TokenConfig
import org.example.project.security.TokenService
import org.example.project.security.hasing.HashingService

fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    val userDao: DAOUser = DAOUserImpl()
    val userProfileDao = DAOUserProfileImpl()
    val postDao = DAOPostImpl()
//    val chatMessageDao = DAOChatMessageImpl()
//    val chatConversationDao = DAOChatConversationImpl()

    routing {
        authRoute(userDao, hashingService, tokenService, tokenConfig)
        profileRoute(userProfileDao)
        postRoute(postDao)
        chatRoute()
    }
}