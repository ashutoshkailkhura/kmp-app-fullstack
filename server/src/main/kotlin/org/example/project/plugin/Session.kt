package org.example.project.plugin

import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession() {

    install(Sessions) {
        cookie<ChatSession>("user_session")
    }

    // This adds an interceptor that will create a specific session in each request if no session is available already.
    intercept(ApplicationCallPipeline.Plugins) {
        if (call.sessions.get<ChatSession>() == null) {
            val userId = call.getUserIdFromPrincipal()
//            val userId = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(userId.toInt(), generateNonce()))
        }
    }
}

/**
 * A chat session is identified by a unique nonce ID. This nonce comes from a secure random source.
 */
data class ChatSession(val userId: Int, val sessionId: String)
data class ChatRequest(val message: String)

