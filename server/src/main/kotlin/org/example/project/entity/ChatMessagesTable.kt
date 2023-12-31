package org.example.project.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

// Chat Messages Table
object ChatMessagesTable : IntIdTable() {
    val senderId = reference("sender_id", UserTable)
    val receiverId = reference("receiver_id", UserTable)
    val conversation = reference("conversation", ChatConversationsTable)
    val messageContent = text("message_content")
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
}

//class ChatMessages(id: EntityID<Int>) : IntEntity(id) {
//    companion object : IntEntityClass<ChatMessages>(ChatMessagesTable)
//
//    val senderId by User referencedOn UserTable.id
//    val receiverId by User referencedOn UserTable.id
//    val conversation by ChatConversations referencedOn ChatConversationsTable.id
//    val content by ChatMessagesTable.messageContent
//    val timestamp by ChatMessagesTable.timestamp
//}