package ui.screens.home

import APIService
import Response
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import data.request.PostRequest
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class HomeUiState(
    val error: String = "",
    val postList: List<entity.Post> = emptyList(),
    val loading: Boolean = false
)

data class CreatePostUiState(
    val result: String = "",
    val loading: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val apiService = APIService()

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    var createPostUiState by mutableStateOf(CreatePostUiState())
        private set


    fun getPost() {
        viewModelScope.launch {
            homeUiState = homeUiState.copy(loading = true)

            homeUiState = when (val result = apiService.getPost()) {
                is Response.Error -> homeUiState.copy(
                    loading = false, error = result.exception.message ?: "Error"
                )

                is Response.Loading -> homeUiState.copy(loading = true)
                is Response.Success -> homeUiState.copy(loading = false, postList = result.data)
            }
        }
    }

    fun createPost(content: String) {
        viewModelScope.launch {
            createPostUiState = createPostUiState.copy(loading = true)

            createPostUiState = when (val result = apiService.createPost(PostRequest(content))) {
                is Response.Error -> createPostUiState.copy(
                    loading = false, result = result.exception.message ?: "Error"
                )

                is Response.Loading -> createPostUiState.copy(loading = true)
                is Response.Success -> createPostUiState.copy(
                    loading = false,
                    result = result.data
                )
            }
        }
    }

    fun resetResult() {
        viewModelScope.launch {
            delay(3_200)
            homeUiState = HomeUiState()
        }
    }
}