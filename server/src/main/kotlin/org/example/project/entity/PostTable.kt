package org.example.project.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

enum class PostType {
    SELLING,
    ADOPTING,
    STORY,
    OTHER
}

object PostTable : Table() {
    val postId = integer("post_id").autoIncrement()
    val userId = integer("user_id").references(UserInfoTable.userId)
    val postType = enumerationByName("post_type", 10, PostType::class)
    val location = varchar("location", 255)
    val postDetails = text("post_details")
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
    // other post details

    override val primaryKey = PrimaryKey(postId)
}