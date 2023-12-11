package core.data.remote

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual class HttpClientFactory {

    actual fun create(tokenProvider: suspend () -> String): HttpClient = HttpClient(Darwin) {
        install(ContentNegotiation) {
            json()
        }
        install(HttpTimeout) {
            requestTimeoutMillis = HttpConfig.REQUEST_TIMEOUT
        }
    }.apply {
        addAuthorization(tokenProvider)
    }
}