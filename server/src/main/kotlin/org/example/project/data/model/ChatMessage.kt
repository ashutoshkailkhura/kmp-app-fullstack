package org.example.project.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatMessage(
    val messageId: Int,
    val conversationId: Int,
    val senderId: Int,
    val receiverId: Int,
    val messageContent: String,
    val timestamp: String
)