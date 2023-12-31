package org.example.project.data.request

import kotlinx.serialization.Serializable

@Serializable
data class PostRequest(
    val content: String
)
