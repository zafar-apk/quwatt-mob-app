@file:OptIn(ExperimentalSerializationApi::class)

package core.data.remote

import io.ktor.client.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

expect class HttpClientFactory() {
    fun create(): HttpClient
}

fun HttpClient.configureForProject(provideAccessToken: suspend () -> String): HttpClient {
    return config {
        install(ContentNegotiation) {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
            }
            json(json = json, contentType = ContentType.Any)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = HttpConfig.REQUEST_TIMEOUT
        }
    }.apply {
        addAuthorizationPlugin(provideAccessToken)
    }
}