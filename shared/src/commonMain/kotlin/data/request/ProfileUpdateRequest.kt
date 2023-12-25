package data.request

import kotlinx.serialization.Serializable

@Serializable
data class ProfileUpdateRequest(
    val name:String,
    val state:String
)
