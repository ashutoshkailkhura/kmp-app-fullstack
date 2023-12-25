package ui.screens.home.postList

import DataUtil
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.ItemPost
import ui.screens.home.postDetail.PostDetailScreen
import ui.screens.home.createPost.CreatePostScreen

class PostListScreen(
    private var listState: LazyListState = LazyListState()
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val expandedFab by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex == 0
            }
        }

        Scaffold(
            modifier = Modifier.padding(bottom = if (listState.firstVisibleItemIndex == 0) 80.dp else 0.dp),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        navigator.push(
                            CreatePostScreen()
                        )
                    },
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
                items(DataUtil.getDummyPostList()) { post ->
                    ItemPost(post) { postId ->
                        navigator.push(
                            PostDetailScreen(postId)
                        )
                    }
                }
            }
        }
    }
}