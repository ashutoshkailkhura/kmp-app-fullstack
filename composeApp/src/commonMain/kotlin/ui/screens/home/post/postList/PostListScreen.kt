package ui.screens.home.post.postList

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.FullScreenError
import ui.components.ItemPost
import ui.components.SimpleLoading
import ui.screens.home.post.PostListUiState
import ui.screens.home.post.PostViewModel
import ui.screens.home.post.createPost.CreatePostScreen
import ui.screens.home.post.postDetail.PostDetailScreen

class PostListScreen(
    private var listState: LazyListState = LazyListState(),
    private val postViewModel: PostViewModel,
) : Screen {

    companion object {
        const val TAG = "PostListScreen"
    }

    @Composable
    override fun Content() {

//        val postViewModel = getViewModel(PostListScreen().key, viewModelFactory { PostViewModel() })
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            println("$TAG LaunchEffect getPost")
            postViewModel.getPost()
        }

        PostListContent(
            uiState = postViewModel.postListUiState,
            onPostClick = {
                navigator.push(
                    PostDetailScreen(it)
                )
            },
            onClickCreatePost = {
                navigator.push(
                    CreatePostScreen()
                )
            },
            reTry = {
                postViewModel.getPost()
            }
        )

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PostListContent(
        uiState: PostListUiState,
        onPostClick: (postId: Int) -> Unit,
        onClickCreatePost: () -> Unit,
        reTry: () -> Unit,

        ) {
        val expandedFab by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex == 0
            }
        }

        if (uiState.loading) {
            SimpleLoading()
        } else if (uiState.error.isNotEmpty()) {
            FullScreenError(uiState.error) {
                reTry()
            }
        } else if (!uiState.loading && uiState.error.isEmpty()) {
            Scaffold(
                modifier = Modifier.padding(bottom = if (listState.firstVisibleItemIndex == 0) 80.dp else 0.dp),
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Home") },
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onClick = { onClickCreatePost() },
                        expanded = expandedFab,
                        icon = { Icon(Icons.Filled.Add, "Localized Description") },
                        text = { Text(text = "Create FAB") },
                    )
                },
                floatingActionButtonPosition = FabPosition.End,
            ) { innerPadding ->
                if (uiState.postList.isNotEmpty()) {
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        items(uiState.postList) { post ->
                            ItemPost(Modifier, post) { postId ->
                                onPostClick(postId)
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                        Text(
                            "No Post Available :(_",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

            }
        }
    }

}

