package org.example.project.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val name:String,
    val state:String
)
