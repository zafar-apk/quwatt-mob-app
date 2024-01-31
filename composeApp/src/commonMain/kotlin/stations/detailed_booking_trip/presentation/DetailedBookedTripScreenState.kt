package tj.ham_safar.app.trips.detailed_booking_trip.presentation

import stations.all.domain.models.BookedTrip

data class DetailedBookedTripScreenState(
    val trip: BookedTrip? = null,
    val error: String? = null,
    val toastError: String? = null,
    val isLoading: Boolean = false,
    val shouldGoBack: Boolean = false,
    val shouldRequestBookingCancellationConfirmation: Boolean = false,
)