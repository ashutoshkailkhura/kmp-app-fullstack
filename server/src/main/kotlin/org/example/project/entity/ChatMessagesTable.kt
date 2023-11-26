package org.example.project.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

enum class ConnectionStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    BLOCKED,
}

object ChatMessagesTable : Table() {
    val messageId = integer("message_id").autoIncrement()
    val senderId = integer("sender_id").references(UserInfoTable.userId)
    val receiverId = integer("receiver_id").references(UserInfoTable.userId)
    val messageContent = text("message_content")
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)

    override val primaryKey = PrimaryKey(messageId)
}