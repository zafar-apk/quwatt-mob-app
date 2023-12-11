package core.data.remote

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

actual class HttpClientFactory {

    actual fun create(tokenProvider: suspend () -> String): HttpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            val json = Json {
                ignoreUnknownKeys = true
                isLenient = true
                explicitNulls = false
            }
            json(json = json, contentType = ContentType.Any)
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(HttpTimeout) {
            requestTimeoutMillis = HttpConfig.REQUEST_TIMEOUT
        }
    }.apply {
        addAuthorization(tokenProvider)
    }
}