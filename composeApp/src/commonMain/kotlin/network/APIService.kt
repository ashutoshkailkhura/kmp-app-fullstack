package network

import entity.Animal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

class APIService {

    companion object {
        const val BASE_URL = "http://192.168.29.57:8080"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getAnimals(): Resource<List<Animal>> {
        val result = client.get("$BASE_URL/animal")

        return if (result.status == HttpStatusCode.OK) {
            Resource.Success(result.body())
        } else {
            Resource.Error(Exception("API Calling Failed"))
        }

    }

    suspend fun addAnimal(newAnimal: Animal): Resource<String> {

        val result = client.post("$BASE_URL/animal") {
            contentType(ContentType.Application.Json)
            setBody(newAnimal)
        }

        return if (result.status == HttpStatusCode.Created) {
            Resource.Success(result.body())
        } else {
            Resource.Error(Exception("API Calling Failed"))
        }
    }

}