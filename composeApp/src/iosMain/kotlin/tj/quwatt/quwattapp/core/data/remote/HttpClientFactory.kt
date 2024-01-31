package tj.quwatt.quwattapp.core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual class HttpClientFactory {

    actual fun create(): HttpClient = HttpClient(Darwin)
}