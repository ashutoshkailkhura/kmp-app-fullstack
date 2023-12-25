package ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class HomeScreen : Screen {

    @OptIn(ExperimentalVoyagerApi::class)
    @Composable
    override fun Content() {
        val listState = rememberLazyListState()
        var bottomVisibility by rememberSaveable { mutableStateOf(true) }

        val tabs by remember {
            mutableStateOf(
                listOf(
                    HomeTab(
                        listState = listState
                    ) { bottomVisibility = it },
                    ChatTab {
                        bottomVisibility = it
                    },
                    ProfileTab {
                        bottomVisibility = it
                    },
                )
            )
        }

        TabNavigator(
            tab = tabs[0],
            tabDisposable = {
                TabDisposable(
                    navigator = it,
                    tabs = tabs
                )
            }
        ) { tabNavigator ->
            Scaffold(
                content = {
                    CurrentTab()
                },

                bottomBar = {
                    AnimatedVisibility(
                        bottomVisibility && listState.firstVisibleItemIndex == 0,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ) {
                        NavigationBar {
                            tabs.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    icon = {
                                        Icon(
                                            item.options.icon!!,
                                            contentDescription = "Localized description"
                                        )
                                    },
                                    label = {
                                        Text(item.options.title)
                                    },
                                    selected = tabNavigator.current.key == item.key,
                                    onClick = {
                                        tabNavigator.current = item
                                    }
                                )
                            }
                        }
                    }
                }
            )
        }
    }
}