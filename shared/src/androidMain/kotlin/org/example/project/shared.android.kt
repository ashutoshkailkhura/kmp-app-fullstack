package org.example.project


//actual class DatabaseDriverFactory() {
//    actual fun createDriver(): SqlDriver {
//        return AndroidSqliteDriver(AppDatabase.Schema, MyApplication.context, "test.db")
//    }
//}
//
//actual fun getDatabaseDriverFactory() = DatabaseDriverFactory()

actual fun getPlatformName(): String = "android"

//actual fun ByteArray.toImageBitmap(): ImageBitmap = toAndroidBitmap().asImageBitmap()
//
//fun ByteArray.toAndroidBitmap(): Bitmap {
//    return BitmapFactory.decodeByteArray(this, 0, size)
//}
