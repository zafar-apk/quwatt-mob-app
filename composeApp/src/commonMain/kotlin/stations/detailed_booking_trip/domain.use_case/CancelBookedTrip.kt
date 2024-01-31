package tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case

import core.domain.util.Resource
import tj.ham_safar.app.trips.detailed_booking_trip.data.remote.CancelBookedTripClient

class CancelBookedTrip(
    private val cancelBookedTripClient: CancelBookedTripClient
) {
    suspend fun execute(id: Int): Resource<Unit> = cancelBookedTripClient.cancelBookedTrip(id)
}