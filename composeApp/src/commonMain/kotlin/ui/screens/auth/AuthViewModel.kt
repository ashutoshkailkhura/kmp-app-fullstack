package ui.screens.auth

import androidx.compose.runtime.mutableStateOf
import data.response.AuthResponse
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.APIService
import network.Resource
import org.jetbrains.compose.resources.ExperimentalResourceApi


data class LogInUiState(
    var result: String = "",
    var navigation: Boolean = false
)

data class SignUpInUiState(
    val result: String = ""
)

@OptIn(ExperimentalResourceApi::class)
class AuthViewModel : ViewModel() {

    private val apiService = APIService()

    private val _logInUiState = MutableStateFlow<Resource<LogInUiState>>(
        Resource.Success(
            LogInUiState()
        )
    )
    val logInUiState: StateFlow<Resource<LogInUiState>> = _logInUiState.asStateFlow()

    private val _signUpUiState =
        MutableStateFlow<Resource<SignUpInUiState>>(Resource.Success(SignUpInUiState()))
    val signUpUiState: StateFlow<Resource<SignUpInUiState>> = _signUpUiState.asStateFlow()

    fun logIn(mail: String, password: String) {

        _logInUiState.value = Resource.Loading

        viewModelScope.launch {
            val result = apiService.logIn(mail, password)
            if (result != null) {
                _logInUiState.value = Resource.Success(LogInUiState(result.token, true))
            } else {
                _logInUiState.value = Resource.Error(Exception("Unable To Connect"))
            }
        }
    }

    fun signUp(mail: String, password: String) {
        _signUpUiState.value = Resource.Loading

        viewModelScope.launch {
            val result = apiService.signUp(mail, password)
            if (result == "Success") {
                _signUpUiState.value = Resource.Success(SignUpInUiState(result))
            } else {
                _signUpUiState.value = Resource.Error(Exception("Unable To Connect"))
            }
        }
    }

    fun resetResult() {
//        viewModelScope.launch {
//            delay(1_000)
//            _logInUiState.value = LogInUiState(false)
//        }
    }


    override fun onCleared() {
        super.onCleared()
    }

}