package ui.screens.home.postList

import DataUtil
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.FullScreenError
import ui.components.ItemPost
import ui.components.SimpleLoading
import ui.components.SlideMessage
import ui.screens.auth.AuthViewModel
import ui.screens.auth.login.LogInScreen
import ui.screens.home.HomeUiState
import ui.screens.home.HomeViewModel
import ui.screens.home.postDetail.PostDetailScreen
import ui.screens.home.createPost.CreatePostScreen

class PostListScreen(
    private var listState: LazyListState = LazyListState()
) : Screen {

    @Composable
    override fun Content() {

        val homeViewModel = getViewModel(PostListScreen().key, viewModelFactory { HomeViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            homeViewModel.getPost()
        }

        PostListContent(
            uiState = homeViewModel.homeUiState,
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
                homeViewModel.resetResult()
                homeViewModel.getPost()
            }
        )

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PostListContent(
        uiState: HomeUiState,
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
        } else if (uiState.postList.isNotEmpty()) {
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
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.padding(it)
                ) {

                    items(uiState.postList) { post ->
                        ItemPost(Modifier, post) { postId ->
                            onPostClick(postId)
                        }
                    }
                }
            }
        }
    }

}

