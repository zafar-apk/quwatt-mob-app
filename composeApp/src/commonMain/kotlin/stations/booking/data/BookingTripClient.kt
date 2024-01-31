package stations.booking.data

import core.domain.util.Resource

interface BookingTripClient {
    suspend fun book(tripId: Int, seatIds: List<Int>): Resource<Unit>
}