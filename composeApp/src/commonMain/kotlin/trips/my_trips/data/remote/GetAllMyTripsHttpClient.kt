package trips.my_trips.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import tj.ham_safar.app.trips.my_trips.data.remote.GetAllMyTripsClient
import trips.all.data.remote.mapper.TripsMapper
import trips.all.data.remote.model.TripDTO
import trips.all.domain.models.Trip

class GetAllMyTripsHttpClient(
    private val client: HttpClient,
    private val mapper: TripsMapper
) : GetAllMyTripsClient {

    override suspend fun getAll(): Resource<List<Trip>> = networkCall(
        map = ::mapAllTripsResult,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/my-trips")
                contentType(ContentType.Application.Json)
            }
        }
    )

    private fun mapAllTripsResult(dto: List<TripDTO>): List<Trip> =
        dto.map(mapper::createTrip)
}