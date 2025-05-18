import org.example.project.data.response.AuthResponse
import org.example.project.db.AppDatabase
import org.example.project.db.UserSetting

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.appDatabaseQueries

    //TODO:    update user setting table into key,value column
    //TODO:    use flow

    internal fun addUserSetting(authResponse: AuthResponse) {
        dbQuery.addUserSetting(
            UserSetting(
                id = 1,
                settingkey = "userId",
                settingvalue = authResponse.userId,
            )
        )
        dbQuery.addUserSetting(
            UserSetting(
                id = 2,
                settingkey = "token",
                settingvalue = authResponse.token,
            )
        )
        dbQuery.addUserSetting(
            UserSetting(
                id = 3,
                settingkey = "mail",
                settingvalue = authResponse.email,
            )
        )
    }

    internal fun getUserSetting(): List<UserSetting> {
        return dbQuery.getUserSetting().executeAsList()
    }

    internal fun clearUserSetting() {
        dbQuery.transaction {
            dbQuery.removeUserSetting()
        }
    }

}