package org.example.project.routes

import org.example.project.data.request.AuthRequest
import org.example.project.data.response.AuthResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils
import org.example.project.dao.DAOUser
import org.example.project.data.KMPConstant
import org.example.project.data.KMPConstant.SUCCESS_MESSAGE
import org.example.project.entity.User
import org.example.project.security.TokenClaim
import org.example.project.security.TokenConfig
import org.example.project.security.TokenService
import org.example.project.security.hasing.HashingService
import org.example.project.security.hasing.SaltedHash

fun Route.authRoute(
    userDao: DAOUser,
    hashingService: HashingService,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, KMPConstant.ERROR_MESSAGE_4)
            return@post
        }

        val areFieldsBlank = request.userEmail.isBlank() || request.password.isBlank()
        val isPwTooShort = request.password.length < 8

        if (areFieldsBlank || isPwTooShort) {
            call.respond(HttpStatusCode.Conflict, KMPConstant.ERROR_MESSAGE_1)
            return@post
        }

//        TODO: check if user already have account

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            userEmail = request.userEmail,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )

        val wasAcknowledged = userDao.addUser(user)

        if (wasAcknowledged == null) {
            call.respond(HttpStatusCode.Conflict, KMPConstant.ERROR_MESSAGE_2)
            return@post
        }

        call.respond(HttpStatusCode.OK, SUCCESS_MESSAGE)
    }

    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, KMPConstant.ERROR_MESSAGE_4)
            return@post
        }

        val user = userDao.getUserByUserEmail(request.userEmail)
        if (user == null) {
            call.respond(HttpStatusCode.Conflict, KMPConstant.ERROR_MESSAGE_3)
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(
                hash = user.password,
                salt = user.salt
            )
        )
        if (!isValidPassword) {
            println("Entered hash: ${DigestUtils.sha256Hex("${user.salt}${request.password}")}, Hashed PW: ${user.password}")
            call.respond(HttpStatusCode.Conflict, KMPConstant.ERROR_MESSAGE_3)
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id.toString()
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token,
                userId = user.id.toString(),
                email = user.userEmail
            )
        )
    }

    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}