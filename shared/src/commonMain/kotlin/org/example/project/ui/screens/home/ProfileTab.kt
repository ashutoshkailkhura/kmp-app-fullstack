//package ui.screens.home
//
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Person
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.remember
//import androidx.compose.ui.graphics.vector.rememberVectorPainter
//import ui.screens.home.profile.ProfileScreen
//
//data class ProfileTab(
//    val hideBottomBar: (status: Boolean) -> Unit = {},
//) : Tab {
//
//    override val options: TabOptions
//        @Composable
//        get() {
//            val icon = rememberVectorPainter(Icons.Default.Person)
//
//            return remember {
//                TabOptions(
//                    index = 2u,
//                    title = "Profile",
//                    icon = icon
//                )
//            }
//        }
//
//    @Composable
//    override fun Content() {
//        val tabTitle = options.title
//
//        LifecycleEffect(
//            onStarted = {
//                println("Navigator Start tab $tabTitle")
//            },
//            onDisposed = {
//                println("Navigator Dispose tab $tabTitle")
//            }
//        )
//
//        Navigator(ProfileScreen()) { navigator ->
//            SlideTransition(navigator) { screen ->
//                LaunchedEffect(Unit) {
//                    hideBottomBar(screen.key == ProfileScreen().key)
//                }
//                screen.Content()
//            }
//        }
//    }
//}