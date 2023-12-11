package trips.booking.presentation

data class BookingTripScreenState(
    val count: Int = 1,
    val isPositiveCounterAvailable: Boolean = true,
    val isNegativeCounterAvailable: Boolean = true,
    val error: String? = null,
    val tripData: TripData? = null,
    val shouldRequestBookingConfirmation: Boolean = false,
    val shouldGoBack: Boolean = false,
    val isLoading: Boolean = false
)

data class TripData(
    val tripId: Int,
    val availableSeatsIds: List<Int>
) {
    val hasAvailableSeats = availableSeatsIds.isNotEmpty()
}