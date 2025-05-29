package org.example.project.ui.screens.home

import org.example.project.AppService
import org.example.project.LocalFlags
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kmpproject.shared.generated.resources.Res
import kmpproject.shared.generated.resources.*
import org.example.project.navigation.ChatDetailScreen
import org.example.project.navigation.ChatListScreen
import org.example.project.navigation.CreatePostScreen
import org.example.project.navigation.PostDetailScreen
import org.example.project.navigation.PostListScreen
import org.example.project.navigation.ProfileScreen
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.example.project.ui.screens.home.chat.ChatListScreen
import org.example.project.ui.screens.home.post.postList.PostListScreen
import org.example.project.ui.screens.home.profile.ProfileScreen
import kotlin.reflect.KClass

data class MainNavDestination(
    val label: String,
    val icon: DrawableResource,
    val route: Any,
    val iconSelected: DrawableResource = icon,
    val routeClass: KClass<*>? = null,
)

@Composable
fun AppMainScreen(
    rootNavController: NavController,
    service: AppService = koinInject(),
) {
//    LaunchedEffect(Unit) {
//        service.completeOnboarding()
//    }

    Column(
        Modifier.fillMaxSize()
//            .background(color = KotlinConfTheme.colors.mainBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        val nestedNavController = rememberNavController()
        NavHost(
            nestedNavController,
            startDestination = PostListScreen,
            modifier = Modifier.fillMaxWidth().weight(1f),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            popEnterTransition = { EnterTransition.None },
            popExitTransition = { ExitTransition.None },
        ) {
            composable<PostListScreen> {
                MainBackHandler()
                PostListScreen(
                    onPostClick = {
                        rootNavController.navigate(PostDetailScreen(it.toString()))
                    },
                    onClickCreatePost = {
                        rootNavController.navigate(CreatePostScreen)
                    })
            }
            composable<ChatListScreen> {
                MainBackHandler()
                ChatListScreen(
                    onUserSelect = { rootNavController.navigate(ChatDetailScreen(it.toString())) },
                )
            }
            composable<ProfileScreen> {
                MainBackHandler()
                ProfileScreen()
            }
        }

        AnimatedVisibility(!isKeyboardOpen(), enter = fadeIn(snap()), exit = fadeOut(snap())) {
            BottomNavigation(nestedNavController)
        }
    }
}

@Composable
private fun MainBackHandler() {
    if (!LocalFlags.current.enableBackOnMainScreens) {
        // Prevent back navigation with an empty handler
        @OptIn(ExperimentalComposeUiApi::class) BackHandler(true) { }
    }
}

@Composable
private fun isKeyboardOpen(): Boolean {
    val bottomInset = WindowInsets.ime.getBottom(LocalDensity.current)
    return rememberUpdatedState(bottomInset > 300).value
}

@Composable
private fun BottomNavigation(nestedNavController: NavHostController) {
    val bottomNavDestinations: List<MainNavDestination> = listOf(
        MainNavDestination(
            label = stringResource(Res.string.nav_destination_schedule),
            icon = Res.drawable.clock_28,
            iconSelected = Res.drawable.clock_28_fill,
            route = PostListScreen,
            routeClass = PostListScreen::class
        ),
        MainNavDestination(
            label = stringResource(Res.string.nav_destination_speakers),
            icon = Res.drawable.team_28,
            iconSelected = Res.drawable.team_28_fill,
            route = ChatListScreen,
            routeClass = ChatListScreen::class
        ),
//        MainNavDestination(
//            label = stringResource(Res.string.nav_destination_map),
//            icon = Res.drawable.location_28,
//            iconSelected = Res.drawable.location_28_fill,
//            route = MapScreen,
//            routeClass = MapScreen::class
//        ),
        MainNavDestination(
            label = stringResource(Res.string.nav_destination_info),
            icon = Res.drawable.info_28,
            iconSelected = Res.drawable.info_28_fill,
            route = ProfileScreen,
            routeClass = ProfileScreen::class
        ),
    )

    val currentDestination = nestedNavController.currentBackStackEntryAsState().value?.destination
    val currentBottomNavDestination = currentDestination?.let {
        bottomNavDestinations.find { dest ->
            val routeClass = dest.routeClass
            routeClass != null && currentDestination.hasRoute(routeClass)
        }
    }

    Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.inversePrimary)
    MainNavigation(
        currentDestination = currentBottomNavDestination,
        destinations = bottomNavDestinations,
        onSelect = {
            nestedNavController.navigate(it.route) {
                // Avoid stacking multiple copies of the main screens
                popUpTo(nestedNavController.graph.findStartDestination().route!!) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
    )
}

@Composable
fun MainNavigation(
    currentDestination: MainNavDestination?,
    destinations: List<MainNavDestination>,
    onSelect: (MainNavDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        destinations.forEach { destination ->
            MainNavigationButton(
                iconResource = destination.icon,
                iconFilledResource = destination.iconSelected,
                contentDescription = destination.label,
                selected = destination == currentDestination,
                onClick = { onSelect(destination) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun MainNavigationButton(
    iconResource: DrawableResource,
    iconFilledResource: DrawableResource,
    contentDescription: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconColor by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary
    )
    Icon(
        modifier = modifier
            .clip(MainNavigationButtonShape)
            .selectable(
                selected = selected,
                enabled = true,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(10.dp)
            .size(28.dp),
        painter = painterResource(if (selected) iconFilledResource else iconResource),
        contentDescription = contentDescription,
        tint = iconColor,
    )
}

private val MainNavigationButtonShape = RoundedCornerShape(8.dp)
