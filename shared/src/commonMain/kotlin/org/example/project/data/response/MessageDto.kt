package org.example.project.data.response

import kotlinx.serialization.Serializable
import org.example.project.data.domain.Message

@Serializable
data class MessageDto(
    val text: String,
    val timestamp: Long,
    val userId: Int
) {
    fun toMessage(): Message {
        return Message(
            msg = text,
            time = timestamp.toString(),
            userName = userId.toString()
        )
    }
}
