package org.example.project.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PostTable : IntIdTable() {
    val user = reference("user", UserTable)
    val content = text("content")
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
}

//class Post(id: EntityID<Int>) : IntEntity(id) {
//
//    companion object : IntEntityClass<Post>(PostTable)
//
//    val user by User referencedOn PostTable.user
//    val content by PostTable.content
//    val timestamp by PostTable.timestamp
//}