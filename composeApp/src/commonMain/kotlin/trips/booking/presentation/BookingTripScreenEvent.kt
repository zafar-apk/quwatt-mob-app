package trips.booking.presentation

sealed class BookingTripScreenEvent {
    object GoBack : BookingTripScreenEvent()
    object BookATrip : BookingTripScreenEvent()
    object OnCountPlus : BookingTripScreenEvent()
    object OnCountMinus : BookingTripScreenEvent()
    object RequestBookingConfirmation : BookingTripScreenEvent()
    object ResetState : BookingTripScreenEvent()
    class OnTripData(
        val tripId: Int,
        val availableSeatsIds: List<Int>
    ) : BookingTripScreenEvent()
}

