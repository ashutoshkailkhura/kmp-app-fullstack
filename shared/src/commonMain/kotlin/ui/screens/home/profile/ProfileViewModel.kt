package ui.screens.home.profile

import SharedSDK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ui.screens.home.OnLineUiState

class ProfileViewModel : ViewModel() {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    private val sdk = SharedSDK

    var onLineUiState by mutableStateOf(false)
        private set

    init {
        println("$TAG init")
        viewModelScope.launch {
            onLineUiState = sdk.remoteApi.isUserConnected()
        }
    }


    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
    }
}