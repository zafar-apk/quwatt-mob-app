package trips.all.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import trips.all.data.remote.mapper.TripsMapper
import trips.all.domain.models.Trips
import trips.all.domain.models.TripsFilter

class AllTripsHttpClient(
    private val client: HttpClient,
    private val mapper: TripsMapper
) : AllTripsClient {

    override suspend fun getTrips(query: TripsFilter?): Resource<Trips> = networkCall(
        map = mapper::createTrips,
        call = {
            client.post {
                url("${AppConstants.BASE_URL}/trips")
                contentType(ContentType.Application.Json)
                setBody(query?.let(mapper::createTripFilterBody))
            }
        }
    )
}