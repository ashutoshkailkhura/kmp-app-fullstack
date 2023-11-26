package org.example.project.entity

import org.jetbrains.exposed.sql.Table

object ConnectionTable : Table() {
    val connectionId = integer("connection_id").autoIncrement()
    val userId1 = integer("user_id_1").references(UserInfoTable.userId)
    val userId2 = integer("user_id_2").references(UserInfoTable.userId)
    val connectionStatus = enumerationByName("connection_status", 20, ConnectionStatus::class)

    override val primaryKey = PrimaryKey(connectionId)
}