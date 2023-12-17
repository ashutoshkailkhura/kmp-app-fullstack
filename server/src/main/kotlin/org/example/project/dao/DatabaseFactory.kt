package org.example.project.dao

import kotlinx.coroutines.*
import org.example.project.entity.PetTable
import org.example.project.entity.PostTable
import org.example.project.entity.UserInfoTable
import org.example.project.entity.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.jdbc.Driver"
        val jdbcURL = "jdbc:mysql://localhost:3306/myPet?characterEncoding=latin1&useConfigs=maxPerformance"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = "ashutosh",
            password = "Ashutosh@123"
        )

        transaction(database) {
            addLogger(StdOutSqlLogger)
            SchemaUtils.drop(
                UserTable,
                UserInfoTable,
                PetTable,
                PostTable
            )
            SchemaUtils.create(
                UserTable,
                UserInfoTable,
                PetTable,
                PostTable
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}