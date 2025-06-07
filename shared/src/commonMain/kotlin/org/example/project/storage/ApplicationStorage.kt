package org.example.project.storage

import kotlinx.coroutines.flow.Flow
import org.example.project.Theme
import org.example.project.Flags

interface ApplicationStorage {
    fun getUserId(): Flow<String?>
    suspend fun setUserId(value: String?)

    fun getPendingUserId(): Flow<String?>
    suspend fun setPendingUserId(value: String?)

    fun isOnboardingComplete(): Flow<Boolean>
    suspend fun setOnboardingComplete(value: Boolean)

    fun getTheme(): Flow<Theme>
    suspend fun setTheme(value: Theme)

//    fun getNotificationSettings(): Flow<NotificationSettings?>
//    suspend fun setNotificationSettings(value: NotificationSettings)

    fun getFlagsBlocking(): Flags?
    fun getFlags(): Flow<Flags?>
    suspend fun setFlags(value: Flags)

    fun ensureCurrentVersion()
}
