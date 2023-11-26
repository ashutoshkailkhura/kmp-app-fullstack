package org.example.project

import SERVER_PORT
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.example.project.dao.DAOUser
import org.example.project.dao.DAOUserImpl
import org.example.project.dao.DatabaseFactory
import org.example.project.plugin.*
import org.example.project.security.JwtTokenService
import org.example.project.security.TokenConfig
import org.example.project.security.hasing.SHA256HashingService

//fun main() {
//    embeddedServer(
//        Netty, port = SERVER_PORT,
//        host = "0.0.0.0",
//        module = Application::module
//    )
//        .start(wait = true)
//}
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
    val userDao: DAOUser = DAOUserImpl()
    DatabaseFactory.init()
    configureSecurity(tokenConfig)

    configureRouting(userDao, hashingService, tokenService, tokenConfig)
//    configureRouting(userDataSource, hashingService, tokenService, tokenConfig)

    configureSerialization()
    configureMonitoring()
}
