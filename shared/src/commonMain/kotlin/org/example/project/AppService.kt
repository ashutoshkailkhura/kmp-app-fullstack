package org.example.project

import io.ktor.util.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.example.project.netio.APIService
import org.example.project.storage.ApplicationStorage

class AppService(
    private val client: APIClient,
    private val timeProvider: TimeProvider,
    private val storage: ApplicationStorage,
//    private val localNotificationService: LocalNotificationService,
    private val scope: CoroutineScope,
    logger: Logger,
) {
    companion object {
        private const val LOG_TAG = "AppService"
    }

    fun getTheme(): Flow<Theme> = flow {
        emit(Theme.SYSTEM)
    }

    fun isOnboardingComplete(): Flow<Boolean> = flow {
//        emit(true)
        emit(false)
    }


}
