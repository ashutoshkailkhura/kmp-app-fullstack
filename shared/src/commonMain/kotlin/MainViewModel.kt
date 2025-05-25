import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    init {
        println("$TAG init")
    }

    private val sdk = SharedSDK


    var mainUiState by mutableStateOf(MainUiState())
        private set

    fun isUserLogIn() {
        println("$TAG isUserLogIn")
        viewModelScope.launch {
            val token = sdk.getToken()
            token?.let {
                mainUiState = mainUiState.copy(userToken = token, loading = false)
            } ?: run {
                mainUiState = mainUiState.copy(loading = false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
    }

}

data class MainUiState(
    val loading: Boolean = true,
    val userToken: String? = null
)

