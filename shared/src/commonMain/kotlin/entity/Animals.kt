package entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animals(

    @SerialName("id")
    val id: String,
    @SerialName("type")
    val type: Int,
    @SerialName("name")
    val name: String,
    @SerialName("story")
    val story: String,
//    val images: List<String>
)
