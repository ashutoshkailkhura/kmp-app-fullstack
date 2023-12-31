import org.example.project.db.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeUserSetting()
        }
    }

    internal fun getUserSetting(): List<String> {
        return dbQuery.getUserSetting().executeAsList()
    }

    internal fun addUserSetting(tokens: String) {
        dbQuery.addUserSetting(tokens)
    }

}