package org.example.project.model

import org.jetbrains.exposed.sql.*

//data class Article(val id: Int, val title: String, val body: String)

object Animals : Table() {
    val id = integer("id").autoIncrement()
    val type = integer("type")
    val name = varchar("title", 128)
    val story = varchar("body", 1024)

    override val primaryKey = PrimaryKey(id)
}