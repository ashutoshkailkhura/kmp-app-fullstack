package ui.screens.home.chat

import DataUtil
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ui.components.ItemChat
import ui.screens.home.createPost.CreatePostScreen


class ChatListScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(DataUtil.chatList) {
                Column {
                    ItemChat(it) {
                        navigator.push(
                            ChatDetailScreen(it)
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .height(1.dp),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                    )
                }
            }
        }
    }

}