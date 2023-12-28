import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import ui.screens.auth.login.LogInScreen
import ui.theme.KMPTheme

@Composable
fun App() {
    KMPTheme {
        Surface(tonalElevation = 5.dp) {
            Navigator(LogInScreen())
        }
    }
}