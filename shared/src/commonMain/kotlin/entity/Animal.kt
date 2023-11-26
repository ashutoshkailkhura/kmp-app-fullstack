package entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
//    @SerialName("id") val id: Int,
    @SerialName("type") val type: Int,
    @SerialName("name") val name: String,
    @SerialName("story") val story: String,
)

