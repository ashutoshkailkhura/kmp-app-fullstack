package ui.screens.home

import SharedSDK
import org.example.project.netio.Response
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.project.data.request.PostRequest
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.entity.Post
import ui.screens.home.post.PostViewModel

class HomeViewModel : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    init {
        println("$TAG init")
    }

    private val sdk = SharedSDK

    var onLineUiState by mutableStateOf(OnLineUiState())
        private set

    fun connectUser() {
        println("$TAG connectUser")
        viewModelScope.launch {
            val userToken = sdk.getToken()
            userToken?.let { token ->
                onLineUiState = onLineUiState.copy(isLoading = true, connected = false)
                onLineUiState =
                    when (val result = sdk.remoteApi.initSession(token)) {
                        is Response.Error -> {
                            println("$TAG connectUser ${result.exception}")
                            onLineUiState.copy(
                                connected = false,
                                isLoading = false,
                                error = result.exception.message ?: "error"
                            )
                        }

                        is Response.Loading -> {
                            println("$TAG connectUser loading ....")
                            onLineUiState.copy(isLoading = true)
                        }

                        is Response.Success -> {
                            println("$TAG connectUser User Connected :)")
                            onLineUiState.copy(
                                isLoading = false,
                                connected = true
                            )
                        }
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
    }
}

data class OnLineUiState(
    val connected: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false
)