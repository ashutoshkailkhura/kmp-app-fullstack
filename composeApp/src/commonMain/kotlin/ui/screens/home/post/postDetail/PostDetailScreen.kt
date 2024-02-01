package ui.screens.home.post.postDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.FullScreenError
import ui.components.SimpleLoading
import ui.screens.home.chat.ChatDetailScreen
import ui.screens.home.post.PostDetailUiState
import ui.screens.home.post.PostViewModel
import ui.screens.home.post.createPost.CreatePostScreen

data class PostDetailScreen(val postId: Int) : Screen {

    @Composable
    override fun Content() {

        val viewModel =
            getViewModel(CreatePostScreen().key, viewModelFactory { PostViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            viewModel.getPostDetail(postId)
        }

        PostDetailScreenContent(
            uiState = viewModel.postDetailUiState,
            onBackPress = navigator::pop,
            onClickContact = {
                navigator.push(ChatDetailScreen(it))
            }
        )


    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PostDetailScreenContent(
        uiState: PostDetailUiState,
        onClickContact: (userId: Int) -> Unit,
        onBackPress: () -> Unit
    ) {

        if (uiState.loading) {
            SimpleLoading()
        } else if (uiState.error.isNotEmpty()) {
            FullScreenError(uiState.error) {

            }
        } else if (uiState.post != null) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Post $postId") },
                        navigationIcon = {
                            IconButton(onClick = { onBackPress() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Localized description"
                                )
                            }
                        }
                    )
                }
            ) {
                LazyColumn(Modifier.padding(it)) {

                    item {
                        Text(uiState.post.postDetail)
                    }
                    item {
                        Spacer(Modifier.height(12.dp))
                    }
                    item {
                        Text("Contact : " + uiState.post.userId.toString(),
                            modifier = Modifier.clickable {
                                onClickContact(uiState.post.userId)
                            })
                    }
                }
            }
        }
    }

}