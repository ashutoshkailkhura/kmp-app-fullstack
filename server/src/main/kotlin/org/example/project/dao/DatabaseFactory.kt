package org.example.project.dao

import kotlinx.coroutines.*
import org.example.project.entity.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
        val driverClassName = "com.mysql.jdbc.Driver"
        val jdbcURL = "jdbc:mysql://localhost:3306/myPet?characterEncoding=latin1&useConfigs=maxPerformance&useFastDateParsing=false"
        val database = Database.connect(
            url = jdbcURL,
            driver = driverClassName,
            user = "ashutosh",
            password = "Ashutosh@123"
        )

        transaction(database) {
            addLogger(StdOutSqlLogger)
//            SchemaUtils.drop(
//                UserTable,
//                UserProfileTable,
//                PostTable,
//                ChatMessagesTable,
//                PetTable
//            )
            SchemaUtils.create(
                UserTable,
                UserProfileTable,
                PostTable,
//                ChatMessagesTable,
//                PetTable
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}