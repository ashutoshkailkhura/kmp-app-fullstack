package ui.screens.home.post

import SharedSDK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.request.PostRequest
import org.example.project.entity.Post
import org.example.project.netio.Response
import ui.screens.home.HomeViewModel

class PostViewModel : ViewModel() {

    companion object {
        const val TAG = "PostViewModel"
    }

    private val sdk = SharedSDK

    var postListUiState by mutableStateOf(PostListUiState())
        private set

    var createPostUiState by mutableStateOf(CreatePostUiState())
        private set

    var _postDetailUiState by mutableStateOf(PostDetailUiState())
        private set


    init {
        println("$TAG init")
    }


    fun getPost() {
        println("${HomeViewModel.TAG} getPost")
        val getPostJob = viewModelScope.launch {
            postListUiState = postListUiState.copy(loading = true)

            postListUiState = when (val result = sdk.remoteApi.getPost(sdk.getToken() ?: "")) {
                is Response.Error -> postListUiState.copy(
                    loading = false, error = result.exception.message ?: "Error"
                )

                is Response.Loading -> postListUiState.copy(loading = true)
                is Response.Success -> postListUiState.copy(loading = false, postList = result.data)
            }
        }
    }

    fun createPost(content: String) {
        viewModelScope.launch {
            createPostUiState = createPostUiState.copy(loading = true)

            createPostUiState = when (val result =
                sdk.remoteApi.createPost(PostRequest(content), sdk.getToken() ?: "")) {
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

    fun getPostDetail(postId: Int) {
        _postDetailUiState = _postDetailUiState.copy(loading = true)
        viewModelScope.launch {

            _postDetailUiState =
                when (val post = sdk.getToken()?.let { sdk.remoteApi.getPostDetail(postId, it) }) {
                    is Response.Error -> _postDetailUiState.copy(
                        loading = false,
                        error = post.exception.message ?: "Something went wrong"
                    )

                    is Response.Loading -> _postDetailUiState.copy(loading = false)
                    is Response.Success -> _postDetailUiState.copy(
                        loading = false,
                        post = post.data
                    )

                    null -> TODO()
                }
        }
    }

    fun resetResult() {
        viewModelScope.launch(Dispatchers.Main) {
            delay(2_200)
            postListUiState = PostListUiState()
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG onCleared")
    }
}

data class CreatePostUiState(
    val result: String = "",
    val loading: Boolean = false
)

data class PostListUiState(
    val error: String = "",
    val postList: List<Post> = emptyList(),
    val loading: Boolean = false
)

data class PostDetailUiState(
    val error: String = "",
    val loading: Boolean = false,
    val post: org.example.project.entity.Post? = null
)
