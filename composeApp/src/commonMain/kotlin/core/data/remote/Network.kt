package core.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.utils.io.errors.IOException
import core.domain.Strings
import core.domain.util.Resource

suspend inline fun <reified T, R> networkCall(
    map: (T) -> R,
    call: () -> HttpResponse
): Resource<R> = runCatching {
    val response = call()
    return if (response.status.isSuccess()) {
        val dto = response.body<T>()
        Resource.Success(map(dto))
    } else {
        Resource.Error(
            RemoteError(
                message = response.bodyAsText(),
                responseCode = response.status.value
            )
        )
    }
}.getOrElse { throwable ->
    throwable.printStackTrace()
    if (throwable is IOException) {
        Resource.Error(
            IOException(
                Strings.cannotConnectToTheServerMessage,
                throwable.cause
            )
        )
    } else {
        Resource.Error(throwable)
    }
}

fun HttpRequestBuilder.authorization(token: String) = header("Authorization", token)

fun HttpClient.addAuthorization(tokenProvider: suspend () -> String) {
    plugin(HttpSend).intercept { request ->
        request.headers.append("Authorization", tokenProvider())
        execute(request)
    }
}