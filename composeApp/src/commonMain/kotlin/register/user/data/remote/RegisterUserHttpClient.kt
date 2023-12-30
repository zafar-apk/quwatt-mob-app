package register.user.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants.BASE_URL
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class RegisterUserHttpClient(
    private val client: HttpClient
) : RegisterUserClient {

    override suspend fun register(
        name: String,
        surname: String,
        patronymic: String,
        dateOfBirth: String,
    ): Resource<Boolean> = networkCall(
        call = {
            client.post("$BASE_URL/users/profile/update") {
                parameter("first_name", name)
                parameter("last_name", surname)
                parameter("patronymic", patronymic)
                parameter("date_of_birth", dateOfBirth)
            }
        },
        map = { httpResponse: HttpResponse ->
            httpResponse.status.isSuccess()
        }
    )
}