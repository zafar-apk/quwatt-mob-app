package tj.ham_safar.app.trips.detailed_trip.presentation

sealed class DetailedTripScreenEvent {
    object OnBackClick : DetailedTripScreenEvent()
    object WriteMessage : DetailedTripScreenEvent()
    object ResetNavigation : DetailedTripScreenEvent()
    object RequestToBookingNavigation : DetailedTripScreenEvent()
    data class NavigateToBooking(
        val tripId: Int, val seatsIds: List<Int>
    ) : DetailedTripScreenEvent()
    data class LoadTrip(val tripId: Int) : DetailedTripScreenEvent()
}