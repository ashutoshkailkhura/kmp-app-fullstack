package org.example.project.entity

import kotlinx.serialization.Serializable


@Serializable
data class WebSocketPayload(
    val type: WebSocketEventType,
    val data: String,
    val targetUserId: String
)

enum class WebSocketEventType {
    CHAT_REQUEST,
    CHAT_REQUEST_RESPONSE,
    NEW_MESSAGE
}
