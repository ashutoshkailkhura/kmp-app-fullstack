package org.example.project

import com.squareup.sqldelight.db.SqlDriver

//import org.example.project.db.AppDatabase

actual fun getPlatformName(): String = "JVM"


//actual class DatabaseDriverFactory {
//    actual fun createDriver(): SqlDriver {
//        return NativeSqliteDriver(AppDatabase.Schema, "test.db")
//    }
//}
//
//actual fun getDatabaseDriverFactory() = DatabaseDriverFactory()
