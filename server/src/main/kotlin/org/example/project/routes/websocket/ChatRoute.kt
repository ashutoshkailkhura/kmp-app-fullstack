package org.example.project.routes.websocket

import com.google.gson.Gson
import com.google.gson.JsonParseException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import org.example.project.dao.DAOChatConversationImpl
import org.example.project.dao.DAOChatMessageImpl
import org.example.project.plugin.ChatSession

private val server = ChatServer()

fun Route.chatRoute(
    chatMessageDao: DAOChatMessageImpl,
    chatConversationDao: DAOChatConversationImpl
) {
//    route("/chat") {
//        // Get Chat request
//        get("request") {
//            val userId = call.getUserIdFromPrincipal()
//            // Retrieve pending chat requests for the user from the database
//            val pendingRequests = getPendingChatRequests(userId)
//            call.respond(pendingRequests)
//        }
//
//        // Send Chat Request
//        /**
//         * userId2 from parameter "/request/{userId2}"
//         * userId1 from jwt
//         * return conversationId
//         * */
//        post("/request/{targetUserId}") {
//
//            val targetUserId = call.parameters["targetUserId"] ?: return@post call.respond(
//                HttpStatusCode.BadRequest,
//                "Target user ID not provided"
//            )
//
////            val message = call.receiveOrNull<ChatRequest>() ?: kotlin.run {
////                call.respond(HttpStatusCode.BadRequest, "Request Body Incorrect")
////                return@post
////            }
//
//            val senderId = call.getUserIdFromPrincipal()
//
//            // Process the chat request, store it in the database, and notify the target user
//            // (You need to implement the logic for this)
//            val conversationId = ChatConversationsTable.insertAndGetId {
//                it[user1_id] = senderId
//                it[user2_id] = targetUserId.toInt()
//            }
//
//            call.respond(HttpStatusCode.OK, conversationId)
//        }
//
//        // Accept Chat Request
//        post("/request/{requestId}/accept") {
//            val requestId = call.parameters["requestId"]?.toIntOrNull()
//            val senderId = call.getUserIdFromPrincipal()
//            if (requestId != null) {
//                // Retrieve the target user's ID and WebSocket session
//                val targetUserId =
//                    chatConversationDao.getChatConversationId() // Replace with actual logic to get the user ID
//                val targetUserSession = userConnections[targetUserId]
//
//                // Send a WebSocket message to the target user
//                targetUserSession?.send(Frame.Text("Your chat request has been accepted!"))
//
//                // Perform other actions, such as updating the ChatConversationTable
//                // with the accepted request or sending a response to the requester
//            }
//
//            call.respond(HttpStatusCode.OK)
//        }
//
//        // Reject Chat Request
//        post("/request/{requestId}/reject") {
//            val requestId = call.parameters["requestId"]?.toIntOrNull()
//            if (requestId != null) {
//                // Retrieve the target user's ID and WebSocket session
//                val targetUserId = "targetUserId" // Replace with actual logic to get the user ID
//                val targetUserSession = userConnections[targetUserId]
//
//                // Send a WebSocket message to the target user
//                targetUserSession?.send(Frame.Text("Your chat request has been rejected."))
//
//                // Optionally, update the conversation status in the database or perform other relevant actions
//            }
//
//            call.respond(HttpStatusCode.OK)
//        }
//
//        // Get Chat History
//        get("/history/{userId}") {
//            val userId = call.parameters["userId"] ?: return@get call.respond(
//                HttpStatusCode.BadRequest,
//                "User ID not provided"
//            )
//
//            val conversationId = ChatConversationsTable.insertAndGetId {
//                it[user1_id] = userId
//                it[user2_id] = targetUserId.toInt()
//            }
//
//            // Retrieve the chat history from the database
//            val chatHistory = chatMessageDao.getChatHistory(conversationId.value)
//            call.respond(chatHistory)
//        }
//    }

    // Defines a websocket `/ws` route that allows a protocol upgrade to convert a HTTP request/response request
    // into a bidirectional packetized connection.
    webSocket("/ws") { // this: WebSocketSession ->

        // First of all we get the session.
        val userSession = call.sessions.get<ChatSession>()

        // We check that we actually have a session. We should always have one,
        // since we have defined an interceptor before to set one.
        if (userSession == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No session"))
            return@webSocket
        }


        // We notify that a member joined by calling the server handler [memberJoin].
        // This allows associating the session ID to a specific WebSocket connection.
        server.memberJoin(userSession, this)

        try {
            // We start receiving messages (frames).
            // Since this is a coroutine, it is suspended until receiving frames.
            // Once the connection is closed, this consumeEach will finish and the code will continue.
            incoming.consumeEach { frame ->
                // Frames can be [Text], [Binary], [Ping], [Pong], [Close].
                // We are only interested in textual messages, so we filter it.
                if (frame is Frame.Text) {
                    // Now it is time to process the text sent from the user.
                    // At this point, we have context about this connection,
                    // the session, the text and the server.
                    // So we have everything we need.
                    receivedMessage(userSession, frame.readText(), this)
                }
            }
        } finally {
            // Either if there was an error, or if the connection was closed gracefully,
            // we notified the server that the member had left.
            server.memberLeft(userSession, this)
        }
    }
}

/**
 * We received a message. Let's process it.
 */
private suspend fun receivedMessage(
    session: ChatSession,
    command: String,
    socketSession: DefaultWebSocketSession
) {
    // We are going to handle commands (text starting with '/') and normal messages

    // Parse the WebSocket message
    val payload = try {
        Gson().fromJson(command, WebSocketPayload::class.java)
    } catch (e: JsonParseException) {
        server.sendTo(session.userId, "server::help", "Invalid JSON format")
        return
    }

    // Process different types of WebSocket events
    when (payload.type) {
        WebSocketEventType.CHAT_REQUEST -> {
            // Process incoming chat request and send appropriate responses
            // Example:
            // - Check if the user is online
            // - Notify the user about the incoming chat request
            // - Send a response back to the sender confirming the request
            // - Update the UI or perform other relevant actions
        }

        WebSocketEventType.CHAT_REQUEST_RESPONSE -> {
            // Process incoming chat request response
            // Example:
            // - Update the UI or perform other relevant actions based on the response
        }

        WebSocketEventType.NEW_MESSAGE -> {
            // Process incoming chat message
            // Example:
            // - Display the message in the chat interface
            server.sendTo(payload.targetUserId,session.sessionId,payload.data)
//            socketSession.sendWebSocketMessage(
//                WebSocketPayload(
//                    WebSocketEventType.NEW_MESSAGE,
//                    payload.data
//                )
//            )
        }
    }

//    when {
        // The command `who` responds the user about all the member names connected to the user.
//        command.startsWith("/who") -> server.who(id)
        // The command `user` allows the user to set its name.
//        command.startsWith("/user") -> {
//            // We strip the command part to get the rest of the parameters.
//            // In this case the only parameter is the user's newName.
//            val newName = command.removePrefix("/user").trim()
//            // We verify that it is a valid name (in terms of length) to prevent abusing
//            when {
//                newName.isEmpty() -> server.sendTo(id, "server::help", "/user [newName]")
//                newName.length > 50 -> server.sendTo(
//                    id,
//                    "server::help",
//                    "new name is too long: 50 characters limit"
//                )
//
//                else -> server.memberRenamed(id, newName)
//            }
//        }
        // The command 'help' allows users to get a list of available commands.
//        command.startsWith("/help") -> server.help(session.sessionId)
        // Total user in chat
//        command.startsWith("/total") -> server.sendTotal(session.userId, 3)
//        // If no commands are matched at this point, we notify about it.
//        command.startsWith("/") -> server.sendTo(
//            session.userId,
//            "server::help",
//            "Unknown command ${command.takeWhile { !it.isWhitespace() }}"
//        )
//        // Handle a normal message.
//        else -> server.message(session.sessionId, command)
//    }
}