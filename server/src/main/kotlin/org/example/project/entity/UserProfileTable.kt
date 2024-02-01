package org.example.project.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object UserProfileTable : IntIdTable() {
    val user = reference("user", UserTable)
    val name = varchar("name", 100)
    val state = varchar("state", 100)
}