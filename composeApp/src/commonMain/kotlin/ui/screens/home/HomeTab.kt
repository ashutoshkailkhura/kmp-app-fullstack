package ui.screens.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition
import ui.screens.home.postList.PostListScreen

data class HomeTab(
    var listState: LazyListState,
    val hideBottomBar: (status: Boolean) -> Unit = {}
) : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon,
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

        Navigator(PostListScreen(listState)) { navigator ->
            SlideTransition(navigator) { screen ->
                LaunchedEffect(Unit) {
                    hideBottomBar(screen.key == PostListScreen().key)
                }
                screen.Content()
            }
        }
    }
}

