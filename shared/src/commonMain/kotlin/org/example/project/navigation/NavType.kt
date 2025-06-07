package org.example.project.navigation

import org.example.project.PostId
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write

internal val PostIdNavType = object : NavType<PostId>(isNullableAllowed = false) {
    override fun get(bundle: SavedState, key: String): PostId = bundle.read { PostId(getString(key)) }
    override fun put(bundle: SavedState, key: String, value: PostId) = bundle.write { putString(key, value.id) }
    override fun parseValue(value: String): PostId = PostId(value)
    override fun serializeAsValue(value: PostId): String = value.id
}
