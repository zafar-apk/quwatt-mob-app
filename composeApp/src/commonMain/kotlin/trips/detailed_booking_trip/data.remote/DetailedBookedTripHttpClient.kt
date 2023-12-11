package tj.ham_safar.app.trips.detailed_booking_trip.data.remote

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import core.data.remote.networkCall
import core.domain.util.AppConstants
import core.domain.util.Resource
import trips.all.domain.models.BookedTrip
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.mapper.DetailedBookedTripMapper

class DetailedBookedTripHttpClient(
    private val client: HttpClient,
    private val mapper: DetailedBookedTripMapper
) : DetailedBookedTripClient {

    override suspend fun getTripById(id: Int): Resource<BookedTrip> = networkCall(
        map = mapper::createBookedTrip,
        call = {
            client.get {
                url("${AppConstants.BASE_URL}/trip/$id")
                contentType(ContentType.Application.Json)
            }
        }
    )
}