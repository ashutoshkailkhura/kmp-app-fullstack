package org.example.project.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object PostTable : IntIdTable() {
    val user = reference("user", UserTable)
    val content = text("content")
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
}