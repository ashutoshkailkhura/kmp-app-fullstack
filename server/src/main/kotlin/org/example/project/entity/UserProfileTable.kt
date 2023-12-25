package org.example.project.entity

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object UserProfileTable : IntIdTable() {
    val user = reference("user", UserTable)
    val name = varchar("name", 100)
    val state = varchar("state", 100)
}

class UserProfile(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserProfile>(UserProfileTable)

    val user by User referencedOn UserProfileTable.user  // use referencedOn for normal references
    val name by UserProfileTable.name
    val state by UserProfileTable.state
}






