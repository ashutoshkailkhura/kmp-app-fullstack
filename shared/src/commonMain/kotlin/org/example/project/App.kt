package org.example.project

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import org.example.project.Theme.DARK
import org.example.project.Theme.LIGHT
import org.example.project.Theme.SYSTEM
import org.example.project.navigation.KMPAppNavHost
import org.example.project.ui.theme.KMPTheme
import org.koin.compose.koinInject
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

    val flags by koinInject<FlagsManager>().flags.collectAsStateWithLifecycle()

    CompositionLocalProvider(LocalFlags provides flags) {
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

}
