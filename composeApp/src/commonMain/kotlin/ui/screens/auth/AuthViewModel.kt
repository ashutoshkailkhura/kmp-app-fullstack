package ui.screens.auth

import SharedSDK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.project.data.request.AuthRequest
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import getPlatformName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.netio.APIService
import org.example.project.netio.Response


data class LogInUiState(
    var token: String = "",
    var result: String = "",
    var loading: Boolean = false
)

data class SignUpInUiState(
    var result: String = "",
    var loading: Boolean = false
)


class AuthViewModel : ViewModel() {

    private val sdk = SharedSDK()

    var token by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            token = sdk.getToken() ?: "error"
        }
    }

    var logInUiState by mutableStateOf(LogInUiState())
        private set

    var signUpUiState by mutableStateOf(SignUpInUiState())
        private set

    fun logIn(mail: String, password: String) {

        if (mail.isEmpty() || password.isEmpty()) {
            logInUiState = logInUiState.copy(result = "Empty field", loading = false)
            return
        }

        viewModelScope.launch {

            logInUiState = logInUiState.copy(loading = true)


            logInUiState = when (val result = sdk.remoteApi.logIn(AuthRequest(mail, password))) {

                is Response.Error -> {
                    logInUiState.copy(
                        result = result.exception.message ?: "error",
                        loading = false
                    )
                }

                is Response.Loading -> {
                    logInUiState.copy(loading = true)
                }

                is Response.Success -> {
                    if (result.data.token.isNotEmpty()) {
                        println("XXX $token")
                        sdk.saveToken(result.data.token)
                        logInUiState.copy(token = result.data.token, loading = false)
                    } else {
                        logInUiState.copy(
                            token = "",
                            loading = false,
                            result = "Something went wrong"
                        )
                    }
                }
            }
        }
    }

    fun signUp(mail: String, password: String) {

        if (mail.isEmpty() || password.isEmpty()) {
            signUpUiState = signUpUiState.copy(result = "Field are empty", loading = false)
            return
        }

        viewModelScope.launch {

            signUpUiState = signUpUiState.copy(result = "", loading = true)


            signUpUiState = when (val result = sdk.remoteApi.signUp(AuthRequest(mail, password))) {

                is Response.Error -> {
                    signUpUiState.copy(
                        result = result.exception.message ?: "error",
                        loading = false
                    )
                }

                is Response.Loading -> {
                    signUpUiState.copy(result = "", loading = true)
                }

                is Response.Success -> {
                    signUpUiState.copy(result = result.data, loading = false)
                }

            }
        }
    }

    fun resetResult() {
        viewModelScope.launch {
            delay(3_200)
            signUpUiState = SignUpInUiState()
            logInUiState = LogInUiState()
        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}