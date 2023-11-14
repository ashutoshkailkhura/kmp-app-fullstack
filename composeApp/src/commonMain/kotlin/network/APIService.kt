package network

import entity.Animals
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
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }

    suspend fun getAnimals(): Resource<List<Animals>> {
        val result = client.get("http://192.168.29.79:8080/animal")

        return if (result.status == HttpStatusCode.OK) {
            Resource.Success(result.body())
        } else {
            Resource.Error(Exception("API Calling Failed"))
        }

    }

    suspend fun addAnimal(newAnimal: Animals): Resource<String> {

        val result = client.post("http://192.168.29.79:8080/animal") {
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