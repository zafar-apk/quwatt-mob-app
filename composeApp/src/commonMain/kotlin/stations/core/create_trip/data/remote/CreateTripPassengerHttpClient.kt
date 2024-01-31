package stations.core.create_trip.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import core.domain.util.AppConstants
import core.domain.util.Resource
import stations.core.create_trip.data.remote.model.CreateTripPassengerRequest
import core.data.remote.networkCall

class CreateTripPassengerHttpClient(
    private val client: HttpClient
) : CreateTripPassengerClient {

    override suspend fun createTrip(request: CreateTripPassengerRequest): Resource<Unit> =
        networkCall(
            map = { _: HttpResponse -> Unit},
            call = {
                client.post {
                    url("${AppConstants.BASE_URL}/passenger/create")
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
        )
}
