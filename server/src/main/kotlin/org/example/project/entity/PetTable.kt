package org.example.project.entity

import org.jetbrains.exposed.sql.Table

object PetTable : Table() {
    val petId = integer("pet_id").autoIncrement()
    val userId = integer("user_id").references(UserInfoTable.userId)
    val petName = varchar("pet_name", 100)
    val age = integer("age")
    val breed = varchar("breed", 100)
    val description = text("description")

    // other pet details
    override val primaryKey = PrimaryKey(petId)
}