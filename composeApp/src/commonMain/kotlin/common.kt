import com.squareup.sqldelight.db.SqlDriver

expect fun getPlatformName(): String

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

expect fun getDatabaseDriverFactory(): DatabaseDriverFactory
