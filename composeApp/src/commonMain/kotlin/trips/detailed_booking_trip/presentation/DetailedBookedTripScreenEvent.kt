package tj.ham_safar.app.trips.detailed_booking_trip.presentation

sealed class DetailedBookedTripScreenEvent {
    object OnBackClick : DetailedBookedTripScreenEvent()
    object GoBack : DetailedBookedTripScreenEvent()
    object WriteMessage : DetailedBookedTripScreenEvent()
    object RequestBookedTripCancellationConfirmation : DetailedBookedTripScreenEvent()
    object ResetState : DetailedBookedTripScreenEvent()
    object CancelBookedTrip : DetailedBookedTripScreenEvent()
    data class LoadTrip(val tripId: Int) : DetailedBookedTripScreenEvent()
}