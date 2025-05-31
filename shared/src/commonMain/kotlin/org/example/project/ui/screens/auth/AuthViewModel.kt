package org.example.project.ui.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.example.project.data.request.AuthRequest
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.Response
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.example.project.AppService


data class LogInUiState(
    var token: String = "",
    var result: String = "",
    var loading: Boolean = false
)

data class SignUpInUiState(
    var result: String = "",
    var loading: Boolean = false
)


class AuthViewModel(
    private val service: AppService
) : ViewModel() {

    companion object {
        const val TAG = "AuthViewModel"
    }

    init {
        println("$TAG init")
    }

    //    TextField
    var logInUserMail by mutableStateOf("")
        private set
    var logInUserPassword by mutableStateOf("")
        private set

    fun updateLogInUserMail(input: String) {
        logInUserMail = input
    }

    fun updateLogInUserPass(input: String) {
        logInUserPassword = input
    }


    //    UI State
    var logInUiState by mutableStateOf(LogInUiState())
        private set


    var signUpUiState by mutableStateOf(SignUpInUiState())
        private set

    fun logIn(mail: String, password: String) {
        println("$TAG logIn")

        if (mail.isEmpty() || password.isEmpty()) {
            logInUiState = logInUiState.copy(result = "Empty field", loading = false)
            return
        }

        viewModelScope.launch {
            logInUiState = logInUiState.copy(loading = true)
            logInUiState = when (val result = service.login(AuthRequest(mail, password))) {
                is Response.Error -> {
                    println("$TAG error ${result.exception.message ?: "error"}")
                    logInUiState.copy(
                        result = result.exception.message ?: "error",
                        loading = false
                    )
                }

                is Response.Loading -> {
                    println("$TAG loading ... ")
                    logInUiState.copy(loading = true)
                }

                is Response.Success -> {
                    println("$TAG success ${result.data}")
                    if (result.data.token.isNotEmpty()) {
//                        sdk.saveToken(result.data)
//                        TODO :- save token locally
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
        println("$TAG signUp")

        if (mail.isEmpty() || password.isEmpty()) {
            signUpUiState = signUpUiState.copy(result = "Field are empty", loading = false)
            return
        }

        viewModelScope.launch {

            signUpUiState = signUpUiState.copy(result = "", loading = true)


            signUpUiState = when (val result = service.signUp(AuthRequest(mail, password))) {

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
        val resetJob = viewModelScope.launch() {
            delay(3_300)
            signUpUiState = SignUpInUiState()
            logInUiState = LogInUiState()
        }
    }

    override fun onCleared() {
        println("$TAG onCleared")
        super.onCleared()
        viewModelScope.cancel()
    }

}