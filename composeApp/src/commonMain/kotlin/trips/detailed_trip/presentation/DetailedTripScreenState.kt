package tj.ham_safar.app.trips.detailed_trip.presentation

import trips.all.domain.models.Trip

data class DetailedTripScreenState(
    val trip: Trip? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val shouldOpenBookingTrip: Boolean = false
) {
    val seatsIds: List<Int>
        get() = trip?.seats
            ?.filter { it.userId == null }
            ?.map { it.id }
            .orEmpty()
}