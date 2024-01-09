package org.example.project.plugin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.sessions.*
import io.ktor.util.*
import javax.naming.AuthenticationException

fun Application.configureSession() {

    install(Sessions) {
        header<UserSession>("user_session")
    }

    // This adds an interceptor that will create a specific session in each request if no session is available already.
    intercept(ApplicationCallPipeline.Plugins) {
        if (call.sessions.get<UserSession>() == null) {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            userId?.let {
                call.sessions.set(UserSession(it, generateNonce()))
            }
        }
    }
}

/**
 * A chat session is identified by a unique nonce ID. This nonce comes from a secure random source.
 */
data class UserSession(val userId: String, val sessionId: String)
