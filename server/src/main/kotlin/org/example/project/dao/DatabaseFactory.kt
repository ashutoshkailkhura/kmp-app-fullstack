package org.example.project.dao

import entity.Animal
import kotlinx.coroutines.*
import org.example.project.model.Animals
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.jdbc.Driver"
        val jdbcURL = "jdbc:mysql://localhost:3306/animal"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = "ashutosh",
            password = "Ashutosh@123"
        )

        transaction(database) {
            // Statements here
            SchemaUtils.create(Animals)

        }

    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}