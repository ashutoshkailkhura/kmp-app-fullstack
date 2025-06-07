package org.example.project

import android.app.Application
import org.example.project.utils.AndroidLogger

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initApp(
            platformLogger = AndroidLogger(),
            platformModule = platformModule(
                application = this,
//                notificationIconId = R.drawable.kotlinconf_notification_icon,
//                notificationConfig = NotificationPlatformConfiguration.Android(
//                    notificationIconResId = R.drawable.kotlinconf_notification_icon,
//                    showPushNotification = true,
//                ),
            ),
        )
    }
}
