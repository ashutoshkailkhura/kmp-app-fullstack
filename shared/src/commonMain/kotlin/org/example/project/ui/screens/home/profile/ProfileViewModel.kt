package org.example.project.ui.screens.home.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.example.project.AppService

class ProfileViewModel(
    private val service: AppService
) : ViewModel() {

    companion object {
        const val TAG = "ProfileViewModel"
    }

    var onLineUiState by mutableStateOf(false)
        private set

    init {
        println("$TAG init")
        viewModelScope.launch {
            onLineUiState = service.isUserConnected()
        }
    }


    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
    }
}