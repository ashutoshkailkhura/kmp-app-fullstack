package org.example.project.plugin

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.example.project.dao.DAOUser
import org.example.project.routes.*
import org.example.project.security.TokenConfig
import org.example.project.security.TokenService
import org.example.project.security.hasing.HashingService

fun Application.configureRouting(
    userDao: DAOUser,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    routing {
        signIn( userDao,hashingService, tokenService, tokenConfig)
        signUp(hashingService, userDao)
        authenticate()
        getSecretInfo()
    }
}