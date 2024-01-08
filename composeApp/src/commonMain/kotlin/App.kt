import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.screens.auth.login.LogInScreen
import ui.screens.home.HomeScreen
import ui.theme.KMPTheme

@Composable
fun App() {

    val mainViewModel = getViewModel(Int.MAX_VALUE, viewModelFactory { MainViewModel() })

    LaunchedEffect(Unit) {
        mainViewModel.isUserLogIn()
    }

    AppContent(
        uiState = mainViewModel.mainUiState
    )

}

@Composable
fun AppContent(uiState: MainUiState) {
    KMPTheme {
        Surface(tonalElevation = 5.dp) {
            if (uiState.userToken.isNotEmpty()) {
                Navigator(HomeScreen())
            } else {
                Navigator(LogInScreen())
            }
        }
    }
}