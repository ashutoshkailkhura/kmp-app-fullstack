package org.example.project.entity

import kotlinx.serialization.Serializable

@Serializable
data class Animals(
    val id:String,
    val type: Int,
    val name: String,
    val story: String,
//    val images: List<String>
)
