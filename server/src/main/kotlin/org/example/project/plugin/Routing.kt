package org.example.project.plugin

import io.ktor.server.routing.*
import io.ktor.server.application.*
import org.example.project.dao.DAOUser
import org.example.project.dao.DAOUserImpl
import org.example.project.routes.*
import org.example.project.security.TokenConfig
import org.example.project.security.TokenService
import org.example.project.security.hasing.HashingService

fun Application.configureRouting(
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    val userDao: DAOUser = DAOUserImpl()

    routing {
        authentication(userDao,hashingService, tokenService, tokenConfig)
    }
}