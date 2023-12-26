sealed class UIResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): UIResource<T>(data)
    class Error<T>(message: String, data: T? = null): UIResource<T>(data, message)
}