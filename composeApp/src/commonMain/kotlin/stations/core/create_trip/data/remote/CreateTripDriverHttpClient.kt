package stations.core.create_trip.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.domain.util.AppConstants
import core.domain.util.Resource
import stations.core.create_trip.data.remote.model.CreateTripDriverRequest
import core.data.remote.networkCall

class CreateTripDriverHttpClient(
    private val client: HttpClient
) : CreateTripDriverClient {

    override suspend fun createTrip(request: CreateTripDriverRequest): Resource<Unit> =
        networkCall(
            map = { _: String -> Unit },
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/trip/create")
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
        )
}