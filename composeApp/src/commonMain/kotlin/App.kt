import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import ui.screens.auth.login.LogInScreen
import ui.screens.home.HomeScreen
import ui.theme.KMPTheme

@Composable
fun App() {

    var isUserLogIn by rememberSaveable { mutableStateOf(false) }
    val sdk = SharedSDK()

    LaunchedEffect(Unit) {
        val token = sdk.getToken()
        println("XXX LaunchedEffect")
        println("XXX $token")
        isUserLogIn = sdk.getToken()?.isNotEmpty() ?: false
    }

    KMPTheme {
        Surface(tonalElevation = 5.dp) {
            if (isUserLogIn) {
                Navigator(HomeScreen())
            } else {
                Navigator(LogInScreen())
            }
        }
    }
}