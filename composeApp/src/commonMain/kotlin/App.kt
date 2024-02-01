import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import cafe.adriel.voyager.transitions.SlideTransition
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.components.SimpleLoading
import ui.screens.auth.authViewModelFactory
import ui.screens.auth.authViewModelKey
import ui.screens.auth.login.LogInScreen
import ui.screens.home.HomeScreen
import ui.screens.home.post.createPost.CreatePostScreen
import ui.theme.KMPTheme

@Composable
fun App() {

    val mainViewModel = getViewModel(Int.MAX_VALUE, viewModelFactory { MainViewModel() })

    LaunchedEffect(Unit) {
        mainViewModel.isUserLogIn()
    }

    AppContent(
        uiState = mainViewModel.mainUiState,
        checkUserLogIn = {
            mainViewModel.isUserLogIn()
        }
    )

}

@Composable
fun AppContent(uiState: MainUiState, checkUserLogIn: () -> Unit) {
    KMPTheme {
        Surface(tonalElevation = 5.dp) {
            if (uiState.loading) {
                SimpleLoading(modifier = Modifier.fillMaxSize())
            } else {
                if (uiState.userToken != null) {
                    Navigator(HomeScreen())
                } else {
                    val authViewModel = getViewModel(authViewModelKey, authViewModelFactory)
                    Navigator(
                        screen = LogInScreen(
                            authViewModel = authViewModel,
                            checkUserLogIn = {
                                checkUserLogIn()
                            }
                        )
                    ) { navigator ->
                        SlideTransition(navigator)
                    }
                }
            }
        }
    }
}