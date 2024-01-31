package tj.ham_safar.app.trips.detailed_booking_trip.data.remote

import core.domain.util.Resource
import stations.all.domain.models.BookedTrip

interface DetailedBookedTripClient {
    suspend fun getTripById(id: Int): Resource<BookedTrip>
}