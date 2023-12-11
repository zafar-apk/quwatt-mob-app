package tj.ham_safar.app.trips.detailed_trip.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import trips.all.data.remote.mapper.TripsMapper
import trips.all.domain.models.Trip

class DetailedTripHttpClient(
    private val client: HttpClient,
    private val tripsMapper: TripsMapper
) : DetailedTripClient {

    override suspend fun getTripById(id: Int): Resource<Trip> = networkCall(
        map = tripsMapper::createTrip,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/trip/$id")
                contentType(ContentType.Application.Json)
            }
        }
    )
}