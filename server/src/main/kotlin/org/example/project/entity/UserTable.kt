package org.example.project.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserTable : IntIdTable() {
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 500)
    val salt = varchar("salt", 500)
    val timestamp = datetime("timestamp").defaultExpression(CurrentDateTime)
}

/**
 * An entity instance or a row in the table is defined as a class instance:
 * */

class User(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<User>(UserTable)

    var email by UserTable.email
    var password by UserTable.password
    var salt by UserTable.salt
    var timestamp by UserTable.timestamp
}