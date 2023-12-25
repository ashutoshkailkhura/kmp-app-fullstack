package org.example.project.dao

import data.request.PostRequest
import entity.Post
import entity.User
import entity.UserProfile
import org.example.project.data.model.ChatMessage
import org.jetbrains.exposed.dao.id.EntityID


interface DAOUser {
    suspend fun addUser(newUser: User): EntityID<Int>?
    suspend fun getUserByUserEmail(userEmail: String): User?
    suspend fun deleteUser(userId: Int): Boolean
}

interface DAOUserProfile {
    suspend fun updateUserProfile(userId: Int, userProfile: UserProfile): EntityID<Int>?
    suspend fun getUserProfile(userId: Int): UserProfile?
}

interface DAOPost {
    suspend fun createPost(userId: Int, post: PostRequest): EntityID<Int>?
    suspend fun getAllPost(): List<Post>
    suspend fun deletePost(postId: Int): Boolean

    suspend fun getPostOfUser(userId: Int): List<Post>
}

interface DAOChatMessage {
    suspend fun insertChatMessage(
        conversationId: Int,
        senderId: Int,
        receiverId: Int,
        messageContent: String
    ): EntityID<Int>?

    suspend fun getChatHistory(conversationId: Int): List<ChatMessage>
    // Add other functions as needed
}

interface DAOChatConversation {
    suspend fun createChatConversation(user1Id: Int, user2Id: Int): EntityID<Int>?
    suspend fun getChatConversationId(user1Id: Int, user2Id: Int): EntityID<Int>?

}
