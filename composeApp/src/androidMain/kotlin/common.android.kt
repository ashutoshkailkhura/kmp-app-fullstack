import org.example.project.MyApplication
import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.example.project.db.AppDatabase


actual class DatabaseDriverFactory() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, MyApplication.context, "test.db")
    }
}

actual fun getDatabaseDriverFactory() = DatabaseDriverFactory()
actual fun getPlatformName(): String = MyApplication.context.packageName

