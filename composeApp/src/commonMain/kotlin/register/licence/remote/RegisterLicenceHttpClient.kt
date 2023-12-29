package register.licence.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource

class RegisterLicenceHttpClient(
    private val client: HttpClient
) : RegisterLicenceClient {

    override suspend fun register(
        licenceNumber: String,
        expirationDate: String
    ): Resource<Boolean> = networkCall(
        map = { response: HttpResponse ->
            response.status.isSuccess()
        },
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/driver-document/create-or-update")
                contentType(ContentType.Application.Json)
                setBody(RegisterLicenceQuery(licenceNumber, expirationDate))
            }
        }
    )
}