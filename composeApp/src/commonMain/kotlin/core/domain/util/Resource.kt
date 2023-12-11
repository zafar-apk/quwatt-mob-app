package core.domain.util

import io.ktor.http.HttpStatusCode
import core.data.remote.RemoteError

sealed class Resource<T>(val data: T?, val throwable: Throwable? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(throwable: Throwable) : Resource<T>(
        data = null,
        throwable = throwable
    ) {

        infix fun withCode(code: HttpStatusCode): Boolean = throwable is RemoteError &&
                throwable.responseCode == code.value
    }
}