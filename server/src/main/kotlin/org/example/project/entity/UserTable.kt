package org.example.project.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : IntIdTable() {
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 500)
    val salt = varchar("salt", 500)
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
}