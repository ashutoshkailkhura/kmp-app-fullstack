package org.example.project.entity

import kotlinx.serialization.Serializable

@Serializable
data class OnlineUser(
    val userId: Int,
    val lastSeen: Long
)
