package org.example.project

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import kotlinx.datetime.Instant
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.Closeable
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.data.request.AuthRequest
import org.example.project.utils.Logger
import org.example.project.data.request.PostRequest
import org.example.project.data.response.AuthResponse
import org.example.project.entity.OnlineUser
import org.example.project.entity.Post
import org.example.project.entity.WebSocketPayload
import org.example.project.Response
import io.ktor.client.plugins.logging.Logger as KtorLogger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.LogLevel

/**
 * Adapter to handle backend API and manage auth information.
 */
class APIClient(
    private val apiUrl: String,
    private val appLogger: Logger,
) : Closeable {

    companion object {
        private const val LOG_TAG = "APIClient"
        const val TAG = "APIService"
        const val BASE_URL = "http://192.168.1.2:8080"
        const val WS_URL = "ws://192.168.1.2:8080/ws"
    }

    var userId: String? = null

    private val client = HttpClient {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : KtorLogger {
                override fun log(message: String) {
                    appLogger.log(LOG_TAG) { message }
                }
            }
        }

        install(WebSockets)

        expectSuccess = true
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }

        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }

        install(DefaultRequest) {
            url.takeFrom(apiUrl)
        }
    }


//    /**
//     * @return status of request.
//     */
//    suspend fun sign(userId: String): Boolean {
//        return safeApiCall {
//            client.post {
//                apiUrl("sign")
//                setBody(userId)
//            }.status.isSuccess()
//        } ?: false
//    }
//
//    /**
//     * Get [ConferenceData] info
//     */
//    suspend fun downloadConferenceData(): Conference? {
//        return safeApiCall {
//            client.get("conference").body()
//        }
//    }
//
//    /**
//     * Vote for session.
//     */
//    suspend fun vote(sessionId: SessionId, score: Score?): Boolean {
//        if (userId == null) return false
//
//        return safeApiCall {
//            client.post {
//                apiUrl("vote")
//                json()
//                setBody(VoteInfo(sessionId, score))
//            }.status.isSuccess()
//        } ?: false
//    }
//
//    /**
//     * Send feedback
//     */
//    suspend fun sendFeedback(sessionId: SessionId, feedback: String): Boolean {
//        if (userId == null) return false
//
//        return safeApiCall {
//            client.post {
//                apiUrl("feedback")
//                json()
//                setBody(FeedbackInfo(sessionId, feedback))
//            }.status.isSuccess()
//        } ?: false
//    }
//
//    /**
//     * List my votes.
//     */
//    suspend fun myVotes(): List<VoteInfo> {
//        if (userId == null) return emptyList()
//
//        return safeApiCall {
//            client.get { apiUrl("vote") }.body<Votes>().votes
//        } ?: emptyList()
//    }
//
    suspend fun getServerTime(): Instant? {
        return safeApiCall {
            client.get { apiUrl("time") }.bodyAsText()
                .let { response -> Instant.fromEpochMilliseconds(response.toLong()) }
        }
    }
//
//    suspend fun getNews(): List<NewsItem>? {
//        return safeApiCall { client.get("news").body<NewsListResponse>().items }
//    }


    private var sockets: WebSocketSession? = null

//    private var chatData: Flow<WebSocketPayload>? = null

    suspend fun signUp(authReq: AuthRequest): Response<String> {

        return withContext(Dispatchers.IO) {
            try {

                Response.Loading

                val result = client.post("$BASE_URL/signup") {
                    contentType(ContentType.Application.Json)
                    setBody(authReq)
                }

                println("$TAG signup $result")

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.bodyAsText())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun logIn(authReq: AuthRequest): Response<AuthResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val result = client.post("$BASE_URL/signin") {
                    contentType(ContentType.Application.Json)
                    setBody(authReq)
                }

                println("$TAG logIn $result")

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun getPost(token: String): Response<List<Post>> {

        return withContext(Dispatchers.IO) {
            try {

                val result = client.get("$BASE_URL/post") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                }
                println("$TAG getPost $result")

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun getPostDetail(postId: Int, token: String): Response<Post> {
        return withContext(Dispatchers.IO) {
            try {
                val result = client.get("$BASE_URL/post/{postId}") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                    parameter("postId", postId)
                }
                println("$TAG getPostDetail $result")

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun createPost(postReq: PostRequest, token: String): Response<String> {
        return withContext(Dispatchers.IO) {
            try {
                val result = client.post("$BASE_URL/post") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                    contentType(ContentType.Application.Json)
                    setBody(postReq)
                }
                println("$TAG createPost $result")
                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun initSession(token: String): Response<Unit> {
        return try {
            sockets = client.webSocketSession {
                header(HttpHeaders.Authorization, "Bearer $token")
                url(WS_URL)
            }
            if (sockets?.isActive == true) {
                Response.Success(Unit)
            } else {
                Response.Error(Exception("Unkonwn error"))
            }
        } catch (ex: Exception) {
            Response.Error(Exception("API Error ${ex.message}"))
        }
    }

    suspend fun isUserConnected(): Boolean {
        return sockets?.isActive == true
    }

    suspend fun getOnlineUser(token: String): Response<List<OnlineUser>> {
        return withContext(Dispatchers.IO) {
            try {
                val result = client.get("$BASE_URL/onlineuser") {
                    headers {
                        append(HttpHeaders.Authorization, "Bearer $token")
                    }
                    contentType(ContentType.Application.Json)
                }
                println("$TAG getOnlineUser $result")
                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun sendMessage(msg: WebSocketPayload): Response<Unit> {
        println("$TAG sendMessage ${Json.encodeToString(msg)}")
        return try {
            sockets?.send(Frame.Text(Json.encodeToString(msg)))
            Response.Success(Unit)
        } catch (ex: Exception) {
            println("$TAG sendMessage ${ex.message}")
            ex.printStackTrace()
            Response.Error(Exception("Unkonwn error"))
        }
    }

    //     look into it, issue with flow,suspend or god knows
    fun observeMsg(): Flow<WebSocketPayload> {
        return try {
            println("$TAG observeMsg")
//            if (sockets?.isActive == true) {
//                println("$TAG observeMsg true")
            sockets?.incoming
                ?.receiveAsFlow()
                ?.mapNotNull { it as? Frame.Text }
                ?.map { it.readText() }
                ?.map {
//                        val json = (it as Frame.Text).readText() ?: ""
                    Json.decodeFromString<WebSocketPayload>(it)
                } ?: flow {}
//            }
//            flow { WebSocketPayload(WebSocketEventType.CHAT_REQUEST, "XXXX", "FUCK") }
        } catch (ex: Exception) {
            println("$TAG sendMessage ${ex.message}")
            ex.printStackTrace()
            flow { }
        }
    }

    suspend fun closeChatSession() {
        sockets?.close()
    }

    /**
     * Runs the [call], returning its result or `null` if exceptions occurred.
     */
    private suspend fun <T> safeApiCall(
        call: suspend () -> T,
    ): T? {
        return try {
            call()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            appLogger.log(LOG_TAG) { "API call failed: ${e.message}" }
            null
        }
    }

    private fun HttpRequestBuilder.json() {
        contentType(ContentType.Application.Json)
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        if (userId != null) {
            header(HttpHeaders.Authorization, "Bearer $userId")
        }

        header(HttpHeaders.CacheControl, "no-cache")

        url {
            encodedPath = path
        }
    }

    override fun close() {
        client.close()
    }
}

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()
}