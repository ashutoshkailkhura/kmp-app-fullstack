package ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import cafe.adriel.voyager.transitions.SlideTransition

object HomeTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = "Home",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        HomeTabContent()
    }
}


@Composable
fun Tab.HomeTabContent() {

    val tabTitle = options.title

    LifecycleEffect(
        onStarted = {
            println("Navigator Start tab $tabTitle")
        },
        onDisposed = {
            println("Navigator Dispose tab $tabTitle")
        }
    )

    Navigator(HomeScreen()) { navigator ->
        SlideTransition(navigator) { screen ->
            screen.Content()
        }
    }

//    Navigator(BasicNavigationScreen(index = 0)) { navigator ->
//        SlideTransition(navigator) { screen ->
//            Column {
//                InnerTabNavigation()
//                screen.Content()
//                println("Navigator Last Event: ${navigator.lastEvent}")
//            }
//        }
//    }
}

class HomeScreen() : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow


        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Home") },
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                items(50) {
                    Card(
                        modifier = Modifier.padding(12.dp).clickable {

                            navigator.push(
                                PostDetail(postId = it)
                            )

                        },
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(12.dp)
                    ) {
                        Text(
                            "item $it",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth().padding(32.dp)
                        )

                    }
                }
            }
        }
    }

}