package ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.request.AuthRequest
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import APIService
import Response


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

    private val apiService = APIService()

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


            logInUiState = when (val result = apiService.logIn(AuthRequest(mail, password))) {

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
                    logInUiState.copy(token = result.data.token, loading = false)
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


            signUpUiState = when (val result = apiService.signUp(AuthRequest(mail, password))) {

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