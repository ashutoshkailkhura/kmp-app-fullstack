package ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screens.home.chat.ChatListScreen
import ui.screens.home.post.postList.PostListScreen

data class ChatTab(
    val hideBottomBar: (status: Boolean) -> Unit = {}
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Chat)

            return remember {
                TabOptions(
                    index = 1u,
                    title = "Chat",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val tabTitle = options.title

        LifecycleEffect(
            onStarted = {
                println("Navigator Start tab $tabTitle")
            },
            onDisposed = {
                println("Navigator Dispose tab $tabTitle")
            }
        )

        Navigator(ChatListScreen()) { navigator ->
            SlideTransition(navigator) { screen ->
                LaunchedEffect(Unit) {
                    hideBottomBar(screen.key == ChatListScreen().key)
                }
                screen.Content()
            }
        }
    }
}