package org.example.project.dao

import org.example.project.dao.DatabaseFactory.dbQuery
import org.example.project.data.model.ChatMessage
import org.example.project.entity.ChatMessagesTable
import org.example.project.entity.UserProfileTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select

class DAOChatMessageImpl : DAOChatMessage {

    override suspend fun insertChatMessage(
        conversationId: Int,
        senderId: Int,
        receiverId: Int,
        messageContent: String
    ): EntityID<Int>? =
        dbQuery {
            val insertChatStatement = ChatMessagesTable.insert { row ->
                row[conversation] = conversationId
//                row[messageContent] = messageContent
//                row[senderId] = senderId
//                row[receiverId] = receiverId
            }
            insertChatStatement.resultedValues?.singleOrNull()?.let {
                it[ChatMessagesTable.id]
            }
        }

    override suspend fun getChatHistory(conversationId: Int): List<ChatMessage> =
        dbQuery {
            ChatMessagesTable.select {
                ChatMessagesTable.conversation eq conversationId
            }.map {
                ChatMessage(
                    messageId = it[ChatMessagesTable.id].value,
                    conversationId = it[ChatMessagesTable.conversation].value,
                    senderId = it[ChatMessagesTable.senderId].value,
                    receiverId = it[ChatMessagesTable.receiverId].value,
                    messageContent = it[ChatMessagesTable.messageContent],
                    timestamp = it[ChatMessagesTable.timestamp].toString()
                )
            }
        }
}