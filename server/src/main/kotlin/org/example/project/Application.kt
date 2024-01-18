package org.example.project

import io.ktor.server.application.*
import org.example.project.dao.DatabaseFactory
import org.example.project.plugin.*
import org.example.project.security.JwtTokenService
import org.example.project.security.TokenConfig
import org.example.project.security.hasing.SHA256HashingService

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
fun Application.module() {
    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    DatabaseFactory.init()

    configureMonitoring()
    configureSerialization()
    configureSecurity(tokenConfig)
    configureSockets()
    configureRouting(hashingService, tokenService, tokenConfig)
    configureSession()

}
