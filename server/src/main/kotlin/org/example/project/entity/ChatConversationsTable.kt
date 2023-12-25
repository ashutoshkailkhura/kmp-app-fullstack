package org.example.project.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

// Chat Conversations Table
object ChatConversationsTable : IntIdTable() {
    val user1_id = reference("user1_id", UserTable)
    val user2_id = reference("user2_id", UserTable)
}

class ChatConversations(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ChatMessages>(ChatMessagesTable)

    val user1_id by User referencedOn UserTable.id
    val user2_id by User referencedOn UserTable.id
}