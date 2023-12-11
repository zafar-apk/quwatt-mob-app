package tj.ham_safar.app.trips.detailed_booking_trip.data.remote

import core.domain.util.Resource

interface CancelBookedTripClient {
    suspend fun cancelBookedTrip(id: Int): Resource<Unit>
}