package org.example.project

import App
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.koin.mp.KoinPlatform

//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen()
        processIntent(intent)
        KoinPlatform.getKoin().declare(PermissionHandler(activity = this))

        setContent {
            App(
                onThemeChange = { isDarkMode ->
                    val systemBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkMode }
                    )
                    enableEdgeToEdge(
                        statusBarStyle = systemBarStyle,
                        navigationBarStyle = systemBarStyle,
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        // Don't enforce scrim https://issuetracker.google.com/issues/298296168
                        window.isNavigationBarContrastEnforced = false
                    }
                },
                popEnterTransition = {
                    scaleIn(initialScale = 1.05f) +
                            fadeIn(animationSpec = tween(50))
                },
                popExitTransition = {
                    scaleOut(targetScale = 0.9f, animationSpec = tween(50)) +
                            fadeOut(animationSpec = tween(50, delayMillis = 50))
                },
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        processIntent(intent)
    }

    private fun processIntent(intent: Intent?) {
        if (intent == null) return

        val notificationId = intent.getStringExtra("localNotificationId")
        if (notificationId != null) {
            // Local notification clicked
//            navigateByLocalNotificationId(notificationId)
            return
        }

        // Process push notifications
//        NotifierManager.onCreateOrOnNewIntent(intent)
    }

}
