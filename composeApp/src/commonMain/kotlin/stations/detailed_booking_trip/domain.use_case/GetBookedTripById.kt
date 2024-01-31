package tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case

import core.domain.util.Resource
import stations.all.domain.models.BookedTrip
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.DetailedBookedTripClient

class GetBookedTripById(
    private val detailedTripClient: DetailedBookedTripClient
) {
    suspend fun execute(id: Int): Resource<BookedTrip> = detailedTripClient.getTripById(id)
}