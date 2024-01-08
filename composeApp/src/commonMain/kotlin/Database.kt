import org.example.project.db.AppDatabase

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    //TODO:    update user setting table into key,value column
    //TODO:    use flow

    internal fun addUserSetting(tokens: String) {
        dbQuery.addUserSetting(tokens)
    }

    internal fun getUserSetting(): List<String> {
        return dbQuery.getUserSetting().executeAsList()
    }

    internal fun clearUserSetting() {
        dbQuery.transaction {
            dbQuery.removeUserSetting()
        }
    }

}