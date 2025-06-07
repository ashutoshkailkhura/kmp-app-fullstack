package org.example.project

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
enum class Theme {
    SYSTEM,
    LIGHT,
    DARK,
}

@Serializable
@JvmInline
value class PostId(val id: String)
