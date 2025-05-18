package ui.screens.home.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.FullScreenError
import ui.components.ItemChat
import ui.components.SimpleLoading


class ChatListScreen : Screen {

    @Composable
    override fun Content() {

        val chatViewModel =
            getViewModel(ChatListScreen().key, viewModelFactory { ChatViewModel() })

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(Unit) {
            println("UI-Compose : ChatListScreen")
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
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading) {
                SimpleLoading(Modifier.fillMaxSize().align(Alignment.Center))
            }
            if (uiState.error.isNotEmpty()) {
                FullScreenError(uiState.error)
            }
            if (uiState.onlineUserList.isEmpty() && uiState.error.isEmpty() && !uiState.isLoading) {
                Text(
                    text = "No One Online Bro ..",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            if (uiState.onlineUserList.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(uiState.onlineUserList) { user ->
                        ItemChat(userId = user.userId) { id ->
                            onUserSelect(id)
                        }
                    }
                }
            }

        }
    }
}