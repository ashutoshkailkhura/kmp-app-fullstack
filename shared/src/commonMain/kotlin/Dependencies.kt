import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.ImageBitmap


interface ImageProvider {
    suspend fun getImage(picture: PictureData): ImageBitmap
//    suspend fun getThumbnail(picture: PictureData): ImageBitmap
//    fun saveImage(picture: PictureData.Camera, image: PlatformStorableImage)
//    fun delete(picture: PictureData)
//    fun edit(picture: PictureData, name: String, description: String): PictureData
}


internal val LocalImageProvider = staticCompositionLocalOf<ImageProvider> {
    noLocalProvidedFor("LocalImageProvider")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
