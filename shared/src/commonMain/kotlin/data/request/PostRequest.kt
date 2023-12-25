package data.request

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val content: String
)
