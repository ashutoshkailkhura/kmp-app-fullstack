import androidx.compose.runtime.Composable
import ui.theme.ReplyTheme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import cafe.adriel.voyager.core.annotation.ExperimentalVoyagerApi
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ui.screen.home.FavoritesTab
import ui.screen.home.HomeTab
import ui.screen.home.ProfileTab

@OptIn(
    ExperimentalResourceApi::class, ExperimentalMaterial3Api::class,
    ExperimentalVoyagerApi::class
)
@Composable
fun App() {
    ReplyTheme(
    ) {

            val tabs = listOf(HomeTab, FavoritesTab, ProfileTab)

            TabNavigator(
                tab = HomeTab,
                tabDisposable = {
                    TabDisposable(
                        navigator = it,
                        tabs = tabs
                    )
                }
            ) { tabNavigator ->
                Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = { Text(text = tabNavigator.current.options.title) },
//                        navigationIcon = {
//                            IconButton(onClick = { /* do something */ }) {
//                                Icon(
//                                    imageVector = Icons.Filled.ArrowBack,
//                                    contentDescription = "Localized description"
//                                )
//                            }
//                        },
//                    )
//                },
                    content = {
                        CurrentTab()
                    },
//                bottomBar = {
//                    val appTabNavigator = LocalTabNavigator.current
//                    NavigationBar {
//                        tabs.forEachIndexed { index, item ->
//                            NavigationBarItem(
//                                icon = {
//                                    Icon(Icons.Outlined.Lock, contentDescription = "Localized description")
//                                },
//                                label = {
//                                    Text(item.options.title)
//                                },
//                                selected = appTabNavigator.current.key == item.key,
//                                onClick = { appTabNavigator.current = item }
//                            )
//                        }
//                    }
//                }
                )
            }




    }
}



//public data class BasicNavigationScreen(
//    val index: Int,
//    val wrapContent: Boolean = false
//) : Screen {
//
//    override val key: ScreenKey = uniqueScreenKey
//
//    @Composable
//    override fun Content() {
//        LifecycleEffect(
//            onStarted = { println("Navigator: Start screen #$index") },
//            onDisposed = { println("Navigator: Dispose screen #$index") }
//        )
//
//        val navigator = LocalNavigator.currentOrThrow
//
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier.run {
//                if (wrapContent) {
//                    padding(vertical = 16.dp).wrapContentHeight()
//                } else {
//                    fillMaxSize()
//                }
//            }
//        ) {
//            Text(
//                text = "Screen #$index",
//                style = MaterialTheme.typography.headlineSmall
//            )
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Row(
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Button(
//                    enabled = navigator.canPop,
//                    onClick = navigator::pop,
//                    modifier = Modifier.weight(.5f)
//                ) {
//                    Text(text = "Pop")
//                }
//
//                Spacer(modifier = Modifier.weight(.1f))
//
//                Button(
//                    onClick = { navigator.push(BasicNavigationScreen(index.inc(), wrapContent)) },
//                    modifier = Modifier.weight(.5f)
//                ) {
//                    Text(text = "Push")
//                }
//
//                Spacer(modifier = Modifier.weight(.1f))
//
//                Button(
//                    onClick = {
//                        navigator.replace(
//                            BasicNavigationScreen(
//                                index.inc(),
//                                wrapContent
//                            )
//                        )
//                    },
//                    modifier = Modifier.weight(.5f)
//                ) {
//                    Text(text = "Replace")
//                }
//            }
//
//            LazyColumn(
//                modifier = Modifier.height(100.dp)
//            ) {
//                items(100) {
//                    Text("Item #$it")
//                }
//            }
//        }
//    }
//}