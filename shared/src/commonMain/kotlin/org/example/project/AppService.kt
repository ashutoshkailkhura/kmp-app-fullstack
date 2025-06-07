package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.data.request.AuthRequest
import org.example.project.data.request.PostRequest
import org.example.project.entity.Post
import org.example.project.entity.WebSocketPayload
import org.example.project.storage.ApplicationStorage
import org.example.project.utils.Logger

class AppService(
    private val client: APIClient,
    private val timeProvider: TimeProvider,
    private val storage: ApplicationStorage,
//    private val localNotificationService: LocalNotificationService,
    private val scope: CoroutineScope,
    logger: Logger,
) {
    companion object {
        private const val LOG_TAG = "AppService"
    }

    fun getTheme(): Flow<Theme> = flow {
        emit(Theme.SYSTEM)
    }

    fun isOnboardingComplete(): Flow<Boolean> = flow {
//        emit(true)
        emit(false)
    }

    suspend fun login(authRequest: AuthRequest) =
        client.logIn(authRequest)

    suspend fun signUp(authReq: AuthRequest) =
        client.signUp(authReq)

    suspend fun getPost(token: String) =
        client.getPost(token)

    suspend fun createPost(postReq: PostRequest, token: String) =
        client.createPost(postReq, token)

    suspend fun getPostDetail(postId: Int, token: String): Response<Post> =
        client.getPostDetail(postId, token)

    suspend fun initSession(token: String) =
        client.initSession(token)

    suspend fun sendMessage(msg: WebSocketPayload) =
        client.sendMessage(msg)

    fun observeMsg() = client.observeMsg()

    suspend fun closeChatSession() =
        client.closeChatSession()

    suspend fun isUserConnected() =
        client.isUserConnected()

    fun getToken() = "dummy token"
}
