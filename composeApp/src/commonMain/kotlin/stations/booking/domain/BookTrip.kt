package stations.booking.domain

import core.domain.util.Resource
import stations.booking.data.BookingTripClient

class BookTrip(
    private val client: BookingTripClient
) {
    suspend operator fun invoke(tripId: Int, seatIds: List<Int>): Resource<Unit> =
        client.book(tripId, seatIds)

}