import Theme.*
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import navigation.KMPAppNavHost
import org.koin.compose.koinInject
import ui.theme.KMPTheme
import kotlin.jvm.JvmSuppressWildcards

@Composable
fun App(
    onThemeChange: ((isDarkTheme: Boolean) -> Unit)? = null,
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)? = null,
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)? = null,
) {

    val service = koinInject<AppService>()
    val currentTheme by service.getTheme().collectAsStateWithLifecycle(initialValue = SYSTEM)
    val isDarkTheme = when (currentTheme) {
        SYSTEM -> isSystemInDarkTheme()
        LIGHT -> false
        DARK -> true
    }

    if (onThemeChange != null) {
        LaunchedEffect(isDarkTheme) { onThemeChange(isDarkTheme) }
    }

    val isOnboardingComplete = service.isOnboardingComplete()
        .collectAsStateWithLifecycle(initialValue = null)
        .value

//    val flags by koinInject<FlagsManager>().flags.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
        KMPTheme(
            darkTheme = isDarkTheme,
//            rippleEnabled = LocalFlags.current.rippleEnabled,
        ) {
            Box(
                Modifier
                    .fillMaxSize()
//                    .background(KMPTheme.colors.mainBackground)
            ) {
                if (isOnboardingComplete != null) {
                    KMPAppNavHost(isOnboardingComplete, popEnterTransition, popExitTransition)
                }
            }
        }
    }

//    KMPTheme {
//        Surface(tonalElevation = 5.dp) {
//            if (uiState.loading) {
//                SimpleLoading(modifier = Modifier.fillMaxSize())
//            } else {
//                if (uiState.userToken != null) {
//                    Navigator(HomeScreen())
//                } else {
//                    val authViewModel = getViewModel(authViewModelKey, authViewModelFactory)
//                    Navigator(
//                        screen = LogInScreen(
//                            authViewModel = authViewModel,
//                            checkUserLogIn = {
//                                checkUserLogIn()
//                            }
//                        )
//                    ) { navigator ->
//                        SlideTransition(navigator)
//                    }
//                }
//            }
//        }
//    }
}