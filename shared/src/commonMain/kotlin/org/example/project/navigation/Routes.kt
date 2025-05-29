package org.example.project.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("AuthScreens")
data object AuthScreen

@Serializable
@SerialName("LogIn")
data object LogInScreen

@Serializable
@SerialName("SignUp")
data object SignUpScreen

@Serializable
@SerialName("Home")
data object AppMainScreen

@Serializable
@SerialName("ChatList")
data object ChatListScreen

@Serializable
@SerialName("ChatDetail")
data class ChatDetailScreen(
    val chatUserId: String
)

@Serializable
@SerialName("PostList")
data object PostListScreen

@Serializable
@SerialName("PostDetail")
data class PostDetailScreen(
    val postId: String
)

@Serializable
@SerialName("CreatePost")
data object CreatePostScreen


@Serializable
@SerialName("Profile")
data object ProfileScreen
