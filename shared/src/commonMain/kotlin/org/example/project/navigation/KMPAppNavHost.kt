package org.example.project.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlin.jvm.JvmSuppressWildcards
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.example.project.ui.screens.auth.login.LogInScreen
import org.example.project.ui.screens.auth.signup.SignUpScreen
import org.example.project.ui.screens.home.AppMainScreen
import org.example.project.ui.screens.home.post.createPost.CreatePostScreen
import org.example.project.ui.screens.home.post.postDetail.PostDetailScreen


@Composable
internal fun KMPAppNavHost(
    isOnboardingComplete: Boolean,
    popEnterTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition)?,
    popExitTransition: @JvmSuppressWildcards (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition)?,
) {
    val navController = rememberNavController()

//    NotificationHandler(navController)
//    PlatformNavHandler(navController)

    val startDestination = if (isOnboardingComplete) AuthScreen else AppMainScreen
    if (popEnterTransition != null && popExitTransition != null) {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
            popEnterTransition = popEnterTransition,
            popExitTransition = popExitTransition,
        ) {
            screens(navController)
        }
    } else {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.fillMaxSize(),
        ) {
            screens(navController)
        }
    }
}

fun NavGraphBuilder.screens(navController: NavHostController) {
    startScreens(
        navController = navController,
    )

    composable<AppMainScreen> {
        AppMainScreen(
            rootNavController = navController,
        )
    }

    composable<CreatePostScreen> {
        val uriHandler = LocalUriHandler.current
        CreatePostScreen(
//            onBack = navController::navigateUp,
//            onGitHubRepo = { uriHandler.openUri(URLs.GITHUB_REPO) },
//            onRateApp = { getStoreUrl()?.let { uriHandler.openUri(it) } },
//            onPrivacyNotice = { navController.navigate(AppPrivacyNoticeScreen) },
//            onJunie = { uriHandler.openUri(URLs.JUNIE_LANDING_PAGE) },
            createPost = { navController.navigate(CreatePostScreen) },
            onBackPress = navController::navigateUp,
            resetResult = {
//                homeViewModel.resetResult()
            }
        )
    }
//    composable<PostDetailScreen> {
//        val params = it.toRoute<PostDetailScreen>()
//        PostDetailScreen(
//            postId = params.postId,
//            onClickContact = TODO(),
//            onBackPress = TODO(),
//            viewModel = TODO()
//        )
//    }

}

fun NavGraphBuilder.startScreens(
    navController: NavHostController,
) {
    navigation<AuthScreen>(
        startDestination = LogInScreen
    ) {
        composable<LogInScreen> {
            LogInScreen(
                onLogInClick = { mail, pass ->
//                    authViewModel.logIn(mail, pass)
                },
                onSignUpClick = {
//                    navigator.push(SignUpScreen(authViewModel))
                },
                resetResult = {
//                    authViewModel.resetResult()
                },
                navigateToHome = {
//                    checkUserLogIn()
                }
            )
        }
        composable<SignUpScreen> {
            SignUpScreen(
                onSignUpClick = { mail, pass ->
//                    authViewModel.signUp(mail, pass)
                    navController.navigate(AppMainScreen)
                },
                resetResult = {
//                    authViewModel.resetResult()
                },
                onBackPressed = {

                }
            )
        }
    }
}
