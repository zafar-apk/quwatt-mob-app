package stations.all.data.remote

import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import stations.all.data.remote.mapper.TripsMapper
import stations.all.domain.models.Trips
import stations.all.domain.models.TripsFilter

class AllTripsHttpClient(
    private val client: HttpClient,
    private val mapper: TripsMapper
) : AllTripsClient {

    override suspend fun getTrips(query: TripsFilter?): Resource<Trips> = networkCall(
        map = mapper::createTrips,
        call = {
            client.get("${AppConstants.BASE_URL}/trips") {
                parameter("role", "DRIVER")
                query?.tripDate?.let { parameter("start_date", it) }
                contentType(ContentType.Application.Json)
            }
        }
    )
}