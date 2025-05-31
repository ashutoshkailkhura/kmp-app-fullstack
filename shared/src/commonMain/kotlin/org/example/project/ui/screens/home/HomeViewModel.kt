package org.example.project.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.AppService
import org.example.project.Response

class HomeViewModel(
    private val service: AppService
) : ViewModel() {

    companion object {
        const val TAG = "HomeViewModel"
    }

    init {
        println("$TAG init")
    }

    var onLineUiState by mutableStateOf(OnLineUiState())
        private set

    fun connectUser() {
        println("$TAG connectUser")
        viewModelScope.launch {
            val userToken = service.getToken()
            userToken?.let { token ->
                onLineUiState = onLineUiState.copy(isLoading = true, connected = false)
                onLineUiState =
                    when (val result = service.initSession(token)) {
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
//        viewModelScope.launch {
//            sdk.remoteApi.closeChatSession()
//        }
        println("$TAG onCleared")
        super.onCleared()
    }
}

data class OnLineUiState(
    val connected: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false
)