package org.example.project.dao

import org.example.project.entity.ChatConversationsTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select

class DAOChatConversationImpl : DAOChatConversation {
    override suspend fun createChatConversation(user1Id: Int, user2Id: Int): EntityID<Int> = DatabaseFactory.dbQuery {
        ChatConversationsTable
            .slice(ChatConversationsTable.id)
            .select {
                (ChatConversationsTable.user1_id eq user1Id and (ChatConversationsTable.user2_id eq user2Id)) or
                        (ChatConversationsTable.user1_id eq user2Id and (ChatConversationsTable.user2_id eq user1Id))
            }.singleOrNull()?.get(ChatConversationsTable.id) ?: ChatConversationsTable
            .insertAndGetId {
                it[this.user1_id] = user1Id
                it[this.user2_id] = user2Id
            }
    }


    override suspend fun getChatConversationId(user1Id: Int, user2Id: Int): EntityID<Int>? =
        DatabaseFactory.dbQuery {
            ChatConversationsTable
                .slice(ChatConversationsTable.id)
                .select {
                    (ChatConversationsTable.user1_id eq user1Id and (ChatConversationsTable.user2_id eq user2Id)) or
                            (ChatConversationsTable.user1_id eq user2Id and (ChatConversationsTable.user2_id eq user1Id))
                }.singleOrNull()
                ?.get(ChatConversationsTable.id)
        }
}