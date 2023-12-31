package org.example.project.data.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val userEmail: String,
    val password: String
)