package trips.all.presentation

import ui.trips.core.models.TripsFilter

sealed class TripsScreenEvent {
    data object OpenTripsHistory : TripsScreenEvent()
    data object OpenTripsMenu : TripsScreenEvent()
    data object ResetNavigation : TripsScreenEvent()
    data object NavigateToLoginScreen : TripsScreenEvent()
    data object NavigateToLicenceScreen : TripsScreenEvent()
    data object NavigateToProfileScreen : TripsScreenEvent()
    data object LoadTrips : TripsScreenEvent()
    data object CreateTrip : TripsScreenEvent()
    data object NavigateToCreateTrip : TripsScreenEvent()
    data object CancelShowingAuthDialog : TripsScreenEvent()
    data class NavigateTripItem(val id: Int) : TripsScreenEvent()
    data class NavigateBookedTripItem(val id: Int) : TripsScreenEvent()
    data class RequestOpenTripItem(val id: Int) : TripsScreenEvent()
    data class RequestOpenBookedTripItem(val id: Int) : TripsScreenEvent()
    data class TripsFilterData(val filter: TripsFilter?) : TripsScreenEvent()
}