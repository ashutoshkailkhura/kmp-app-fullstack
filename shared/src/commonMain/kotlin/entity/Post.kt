package entity

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val postId: Int,
    val userId: Int,
    val postDetail: String,
    val timeStamp:String
)