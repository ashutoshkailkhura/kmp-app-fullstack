package org.example.project.entity

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserInfoTable : Table() {
    val userId = integer("user_id").references(UserTable.userId)
    val userName = varchar("user_name", 100)
    val userEmail = varchar("email", 100).uniqueIndex()
    val gender = varchar("gender", 10)
    val address = text("address")
    val profileImage = varchar("profile_image", 255).nullable()
    val mobileNumber = varchar("mobile_number", 15).nullable()
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)

    // other user details
    override val primaryKey = PrimaryKey(userId)
}









