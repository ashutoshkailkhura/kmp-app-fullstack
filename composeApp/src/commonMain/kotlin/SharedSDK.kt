import org.example.project.data.response.AuthResponse
import org.example.project.netio.APIService

object SharedSDK {

    private val localDb = Database(getDatabaseDriverFactory())
    val remoteApi = APIService()

    suspend fun saveToken(data: AuthResponse) = localDb.addUserSetting(data)
    suspend fun getToken(): String? {
        val userSetting = localDb.getUserSetting().filter {
            it.settingkey == "token"
        }
        return userSetting.firstOrNull()?.settingvalue
    }

}