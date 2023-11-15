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
import kotlinx.coroutines.delay
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

    suspend fun getAnimals(): Resource<List<Animal>> {
//        val result = client.get("http://192.168.29.79:8080/animal")
//
//        return if (result.status == HttpStatusCode.OK) {
//            Resource.Success(result.body())
//        } else {
//            Resource.Error(Exception("API Calling Failed"))
//        }
        delay(2_000)
        return Resource.Success(
            listOf(
                Animal(
                    1,
                    1,
                    "ashu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    2,
                    2,
                    "basu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    3,
                    3,
                    "manu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    4,
                    4,
                    "samu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    1,
                    1,
                    "ashu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    2,
                    2,
                    "basu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    3,
                    3,
                    "manu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
                Animal(
                    4,
                    4,
                    "samu",
                    "as as asasa asas as as as as as sd df df df df fddfdfdf df df df df df df df df"
                ),
            )
        )

    }

    suspend fun addAnimal(newAnimal: Animal): Resource<String> {

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