package ui.screens.home.post

import SharedSDK
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.project.data.request.PostRequest
import org.example.project.entity.Post
import org.example.project.netio.Response

class PostViewModel : ViewModel() {

    companion object {
        const val TAG = "PostViewModel"
    }

    private val sdk = SharedSDK

    var postListUiState by mutableStateOf(PostListUiState())
        private set

    var createPostUiState by mutableStateOf(CreatePostUiState())
        private set

    var postDetailUiState by mutableStateOf(PostDetailUiState())
        private set


    init {
        println("$TAG init")
    }


    fun getPost() {
        println("$TAG getPost")
        postListUiState = PostListUiState()
        viewModelScope.launch {
            postListUiState = postListUiState.copy(loading = true)
            postListUiState = when (val result = sdk.remoteApi.getPost(sdk.getToken() ?: "")) {
                is Response.Error -> {
                    println("$TAG getPost ${result.exception}")
                    postListUiState.copy(
                        loading = false, error = result.exception.message ?: "Error"
                    )
                }

                is Response.Loading -> {
                    println("$TAG getPost loading ...")
                    postListUiState.copy(loading = true)
                }

                is Response.Success -> {
                    println("$TAG getPost : ${result.data.size}")
                    postListUiState.copy(loading = false, postList = result.data)
                }
            }
        }
    }

    fun createPost(content: String) {
        println("$TAG createPost")
        viewModelScope.launch {
            createPostUiState = createPostUiState.copy(loading = true)

            createPostUiState = when (val result =
                sdk.remoteApi.createPost(PostRequest(content), sdk.getToken() ?: "")) {
                is Response.Error -> {
                    println("$TAG createPost ${result.exception.message ?: "Error"}")
                    createPostUiState.copy(
                        loading = false, result = result.exception.message ?: "Error"
                    )
                }

                is Response.Loading -> {
                    println("$TAG createPost Loading ...")
                    createPostUiState.copy(loading = true)
                }

                is Response.Success -> {
                    println("$TAG createPost ${result.data}")
                    createPostUiState.copy(
                        loading = false,
                        result = result.data
                    )
                }
            }
        }
    }

    fun getPostDetail(postId: Int) {
        println("$TAG getPostDetail")
        postDetailUiState = postDetailUiState.copy(loading = true)
        viewModelScope.launch {
            sdk.getToken()?.let { userToken ->
                postDetailUiState =
                    when (val post = sdk.remoteApi.getPostDetail(postId, userToken)) {
                        is Response.Error -> {
                            postDetailUiState.copy(
                                loading = false,
                                error = post.exception.message ?: "Something went wrong"
                            )
                        }

                        is Response.Loading -> {
                            postDetailUiState.copy(loading = true)
                        }

                        is Response.Success -> {
                            postDetailUiState.copy(
                                loading = false,
                                post = post.data
                            )
                        }
                    }
            }
        }
    }

    fun resetResult() {
        viewModelScope.launch {
            delay(3_200)
            createPostUiState = CreatePostUiState()
            postListUiState = PostListUiState()
            postDetailUiState = PostDetailUiState()
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
    val post: Post? = null
)
