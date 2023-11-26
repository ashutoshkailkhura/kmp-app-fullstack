package org.example.project.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : Table() {
    val userId = integer("user_id").autoIncrement()
    val userEmail = varchar("email", 100).uniqueIndex()
    val userPassword = varchar("password", 500)
    val salt = varchar("salt", 500)
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)

    // other user details
    override val primaryKey = PrimaryKey(userId)
}