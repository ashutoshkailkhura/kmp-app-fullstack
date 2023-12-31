import org.example.project.netio.APIService

class SharedSDK() {

    private val localDb = Database(getDatabaseDriverFactory())
    val remoteApi = APIService()

    suspend fun saveToken(token: String) = localDb.addUserSetting(token)

    suspend fun getToken(): String? = localDb.getUserSetting().firstOrNull()

}