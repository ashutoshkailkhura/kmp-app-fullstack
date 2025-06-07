package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.example.project.storage.ApplicationStorage
import org.example.project.storage.MultiplatformSettingsStorage
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.screens.home.HomeViewModel
import org.example.project.ui.screens.home.chat.ChatViewModel
import org.example.project.ui.screens.home.post.PostViewModel
import org.example.project.ui.screens.home.profile.ProfileViewModel
import org.example.project.utils.DebugLogger
import org.example.project.utils.Logger
import org.example.project.utils.NoopProdLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initApp(
    platformLogger: Logger,
    platformModule: Module,
    flags: Flags = Flags(),
) {
    val koin = initKoin(platformLogger, platformModule, flags)
//    initNotifier(configuration = koin.get(), logger = koin.get())
}

private fun initKoin(
    platformLogger: Logger,
    platformModule: Module,
    platformFlags: Flags,
): Koin {
    return startKoin {
        val appModule = module {
            single<ApplicationStorage> { MultiplatformSettingsStorage(get()) }
            single {
                val flags = get<ApplicationStorage>().getFlagsBlocking()
                val endpoint = when {
                    flags != null && (flags != platformFlags) -> URLs.STAGING_URL
                    else -> URLs.PRODUCTION_URL
                }
                APIClient(endpoint, get())
            }
            single<TimeProvider> {
                val flags = get<ApplicationStorage>().getFlagsBlocking()
                when {
                    flags != null && flags.useFakeTime -> FakeTimeProvider(get())
                    else -> ServerBasedTimeProvider(get())
                }
            }
            single<Logger> {
                val flags = get<ApplicationStorage>().getFlagsBlocking()
                when {
                    flags != null && flags.debugLogging -> DebugLogger(platformLogger)
                    else -> NoopProdLogger()
                }
            }
            single { FlagsManager(platformFlags, get(), get()) }
            single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }
            singleOf(::AppService)
        }

        val viewModelModule = module {
            viewModelOf(::AuthViewModel)
            viewModelOf(::HomeViewModel)
            viewModelOf(::PostViewModel)
            viewModelOf(::ChatViewModel)
            viewModelOf(::ProfileViewModel)
        }

        // Note that the order of modules here is significant, later
        // modules can override dependencies from earlier modules
        modules(platformModule, appModule, viewModelModule)
    }.koin
}