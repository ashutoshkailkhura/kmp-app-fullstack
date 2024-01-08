package ui.screens.home.chat

import DataUtil
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.ItemChat
import ui.screens.home.post.createPost.CreatePostScreen


class ChatListScreen : Screen {

    @Composable
    override fun Content() {

        val chatViewModel =
            getViewModel(ChatListScreen().key, viewModelFactory { ChatViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            chatViewModel.getOnlineUser()
        }
        ChatListScreenContent(
            uiState = chatViewModel.onlineUserChatListUiState,
            onUserSelect = {
                navigator.push(
                    ChatDetailScreen(it)
                )
            }
        )

    }

    @Composable
    fun ChatListScreenContent(
        uiState: OnlineUserChatListUiState,
        onUserSelect: (userId: Int) -> Unit
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(uiState.onlineUserList) { user ->
                ItemChat(userId = user.userId) { id ->
                    onUserSelect(id)
                }
            }
        }
    }

}