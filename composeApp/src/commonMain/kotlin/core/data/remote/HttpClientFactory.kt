package core.data.remote

import io.ktor.client.*

expect class HttpClientFactory() {
    fun create(tokenProvider: suspend () -> String): HttpClient
}