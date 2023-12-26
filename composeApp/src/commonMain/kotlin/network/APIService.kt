package network

import data.request.AuthRequest
import data.response.AuthResponse
import entity.Animal
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val exception: Exception) : Response<Nothing>()
    object Loading : Response<Nothing>()
}

class APIService {

    companion object {
        const val BASE_URL = "http://192.168.1.5:8080"
    }

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
    }


    suspend fun signUp(authReq: AuthRequest): Response<String> {

        return withContext(Dispatchers.IO) {
            try {

                Response.Loading

                val result = client.post("$BASE_URL/signup") {
                    contentType(ContentType.Application.Json)
                    setBody(authReq)
                }

                println("UI State ${result}")

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.bodyAsText())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun logIn(authReq: AuthRequest): Response<AuthResponse> {

        return withContext(Dispatchers.IO) {
            try {

//                Response.Loading

                val result = client.post("$BASE_URL/signin") {
                    contentType(ContentType.Application.Json)
                    setBody(authReq)
                }

                if (result.status == HttpStatusCode.OK) {
                    Response.Success(result.body())
                } else {
                    // Handle different HTTP status codes
                    when (result.status) {
                        HttpStatusCode.BadRequest -> Response.Error(Exception("Bad Request: ${result.body<String>()}"))
                        HttpStatusCode.Unauthorized -> Response.Error(Exception("Unauthorized: ${result.body<String>()}"))
                        // Add more cases for other HTTP status codes as needed
                        else -> Response.Error(Exception("API Error ${result.status.value}: ${result.body<String>()}"))
                    }
                }
            } catch (e: Exception) {
                Response.Error(Exception("API Error ${e.message}"))
            }
        }
    }

    suspend fun getAnimals(): Response<List<Animal>> {
        val result = client.get("$BASE_URL/animal")

        return if (result.status == HttpStatusCode.OK) {
            Response.Success(result.body())
        } else {
            Response.Error(Exception("API Calling Failed"))
        }

    }

    suspend fun addAnimal(newAnimal: Animal): Response<String> {

        val result = client.post("$BASE_URL/animal") {
            contentType(ContentType.Application.Json)
            setBody(newAnimal)
        }

        return if (result.status == HttpStatusCode.Created) {
            Response.Success(result.body())
        } else {
            Response.Error(Exception("API Calling Failed"))
        }
    }

}